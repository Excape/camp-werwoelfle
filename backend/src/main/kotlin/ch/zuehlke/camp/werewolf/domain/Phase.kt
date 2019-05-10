package ch.zuehlke.camp.werewolf.domain

import ch.zuehlke.camp.werewolf.service.CommunicationService
import ch.zuehlke.camp.werewolf.service.RoleService
import ch.zuehlke.camp.werewolf.service.VotingService

abstract class Phase(val allPlayers: List<Player>) {
    abstract fun execute()
    open fun isActive(): Boolean {
        return !isGameOver()
    }

    private fun isGameOver(): Boolean {
        // TODO: add condition for victory of amor couple
        return allPlayers.allVillagesAreDead() || allPlayers.allWerewolvesAreDead()
    }

    abstract fun sendStartPhaseCommand()

    fun getAlivePlayersWithRole(role: Role): List<Player> {
        return alivePlayers
            .filter { it.role == role }
    }

    val alivePlayers get() = allPlayers.filter { it.playerState == PlayerState.ALIVE }

    protected fun killOffVotedPlayers(players: List<Player>) {
        players.forEach {
            it.playerState = PlayerState.DYING
        }
    }
}

class RolePhase(
    private val gameName: String,
    private val roleService: RoleService,
    private val communicationService: CommunicationService,
    allPlayers: List<Player>
) : Phase(allPlayers) {
    override fun sendStartPhaseCommand() {
        communicationService.sendGameCommand(gameName, GameCommand.PHASE_ROLE)
    }

    private var alreadyRun = false

    override fun isActive(): Boolean {
        return !alreadyRun && super.isActive()
    }

    override fun execute() {
        roleService.generateRoles(allPlayers)
        val messageByPlayerMap = allPlayers.associateBy({ it }, { RoleOutboundMessage(it.role!!) })
        communicationService.communicate(gameName, InboundType.ACK, messageByPlayerMap)
        alreadyRun = true
    }
}

class NightfallPhase(
    private val gameName: String,
    private val communicationService: CommunicationService,
    allPlayers: List<Player>
) : Phase(allPlayers) {
    override fun sendStartPhaseCommand() {
        communicationService.sendGameCommand(gameName, GameCommand.PHASE_NIGHTFALL)
    }

    override fun execute() {
        communicationService.communicate(gameName, GetAckOutboundMessage(), InboundType.ACK, allPlayers)
    }
}

class WerewolfPhase(
    private val gameName: String,
    private val communicationService: CommunicationService,
    private val votingService: VotingService,
    allPlayers: List<Player>
) : Phase(allPlayers) {
    override fun sendStartPhaseCommand() {
        communicationService.sendGameCommand(gameName, GameCommand.PHASE_WEREWOLF)
    }

    override fun isActive(): Boolean {
        return alivePlayers
            .any { it.role == Role.WEREWOLF } && super.isActive()
    }

    override fun execute() {
        val werewolves = getAlivePlayersWithRole(Role.WEREWOLF)
        val villagers = getAlivePlayersWithRole(Role.VILLAGER)

        val voting = createVoting(werewolves, villagers)
        val votingResult = votingService.startVoting(gameName, voting)

        killOffVotedPlayers(votingResult.electedPlayers)
    }

    private fun createVoting(
        werewolves: List<Player>,
        villagers: List<Player>
    ): Voting {
        return Voting(
            voters = werewolves,
            candidates = villagers,
            votesPerPlayer = 1,
            numberOfSeats = 1
        )
    }
}

class WakeUpPhase(
    private val gameName: String,
    private val communicationService: CommunicationService,
    allPlayers: List<Player>
) : Phase(allPlayers) {
    override fun sendStartPhaseCommand() {
        communicationService.sendGameCommand(gameName, GameCommand.PHASE_WAKEUP)
    }

    override fun execute() {
        val dyingPlayers = allPlayers.filter { it.playerState == PlayerState.DYING }
        communicationService.communicate(
            gameName,
            DeadPlayersOutboundMessage(dyingPlayers),
            InboundType.ACK,
            allPlayers
        )
        dyingPlayers.forEach { it.playerState = PlayerState.DEAD }
    }
}

class DayPhase(
    private val gameName: String,
    private val communicationService: CommunicationService,
    private val votingService: VotingService,
    allPlayers: List<Player>
) : Phase(allPlayers) {
    override fun sendStartPhaseCommand() {
        communicationService.sendGameCommand(gameName, GameCommand.PHASE_DAY)
    }

    override fun execute() {
        val voting = createVoting(alivePlayers)
        val votingResult = votingService.startVoting(gameName, voting)

        val dyingPlayers = votingResult.electedPlayers
        killOffVotedPlayers(dyingPlayers)


    }

    private fun createVoting(
        villagers: List<Player>
    ): Voting {
        return Voting(
            voters = villagers,
            candidates = villagers,
            votesPerPlayer = 1,
            numberOfSeats = 1
        )
    }
}

class ExecutionPhase(
    private val gameName: String,
    private val communicationService: CommunicationService,
    allPlayers: List<Player>
) : Phase(allPlayers) {
    override fun execute() {
        val dyingPlayers = allPlayers.filter { it.playerState == PlayerState.DYING }
        communicationService.communicate(
            gameName,
            DeadPlayersOutboundMessage(dyingPlayers),
            InboundType.ACK,
            allPlayers
        )
        dyingPlayers.forEach { it.playerState = PlayerState.DEAD }
    }

    override fun sendStartPhaseCommand() {
        communicationService.sendGameCommand(gameName, GameCommand.PHASE_EXECUTION)
    }

}

class GameOverPhase(
    private val gameName: String,
    private val communicationService: CommunicationService,
    allPlayers: List<Player>
) : Phase(allPlayers) {

    override fun sendStartPhaseCommand() {
        communicationService.sendGameCommand(gameName, GameCommand.PHASE_GAME_OVER)
    }

    override fun execute() {
        val winningRole = if (allPlayers.allVillagesAreDead()) {
            Role.WEREWOLF
        } else {
            Role.VILLAGER
        }
        communicationService.communicateOneWay(gameName, GameOverOutboundMessage(winningRole), allPlayers)
    }

}