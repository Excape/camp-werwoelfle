package ch.zuehlke.camp.werewolf.domain

import ch.zuehlke.camp.werewolf.service.CommunicationService
import ch.zuehlke.camp.werewolf.service.RoleService
import ch.zuehlke.camp.werewolf.service.VotingService

abstract class Phase(val allPlayers: List<Player>) {
    abstract fun execute()
    abstract fun isActive(): Boolean
    abstract fun sendStartPhaseCommand()

    fun getAlivePlayersWithRole(role: Role): List<Player> {
        return alivePlayers
            .filter { it.role == role }
    }

    val alivePlayers get() = allPlayers.filter{it.playerState == PlayerState.ALIVE}

    protected fun killOffVotedPlayers(players: List<Player>, killedBy: Role) {
        players.forEach {
            it.playerState = when(killedBy) {
                Role.WEREWOLF -> PlayerState.DYING
                else -> PlayerState.DEAD
            }
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
        return !alreadyRun
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
       communicationService.communicate(gameName, GetAckOutboundMessage(), InboundType.ACK, allPlayers )
    }

    override fun isActive(): Boolean {
        return true
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
            .any { it.role == Role.WEREWOLF }
    }

    override fun execute() {
        val werewolves = getAlivePlayersWithRole(Role.WEREWOLF)
        val villagers = getAlivePlayersWithRole(Role.VILLAGER)

        val voting = createVoting(werewolves, villagers)
        val votingResult = votingService.startVoting(gameName, voting)

        killOffVotedPlayers(votingResult.electedPlayers, Role.WEREWOLF)
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
        communicationService.communicate(gameName, DeadPlayersOutboundMessage(dyingPlayers), InboundType.ACK, allPlayers)
        dyingPlayers.forEach { it.playerState = PlayerState.DEAD }
    }

    override fun isActive(): Boolean {
        return true
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
        killOffVotedPlayers(dyingPlayers, Role.VILLAGER)

        communicationService.communicate(
            gameName,
            DeadPlayersOutboundMessage(dyingPlayers.toList()),
            InboundType.ACK,
            allPlayers
        )
    }

    override fun isActive(): Boolean {
        return true
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