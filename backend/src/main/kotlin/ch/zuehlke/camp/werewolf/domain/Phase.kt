package ch.zuehlke.camp.werewolf.domain

import ch.zuehlke.camp.werewolf.service.CommunicationService
import ch.zuehlke.camp.werewolf.service.RoleService

interface Phase {
    fun execute()
    fun isActive(): Boolean
    fun sendStartPhaseCommand()
}

class RolePhase(
    private val gameName: String,
    private val roleService: RoleService,
    private val communicationService: CommunicationService,
    private val allPlayers: List<Player>
) : Phase {
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
    private val allPlayers: List<Player>
) : Phase {
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
    private val allPlayers: List<Player>
) : Phase {
    override fun sendStartPhaseCommand() {
        communicationService.sendGameCommand(gameName, GameCommand.PHASE_WEREWOLF)
    }

    override fun isActive(): Boolean {
        return allPlayers.filter {
            it.role == Role.WEREWOLF
        }.any {
            it.playerState != PlayerState.DEAD
        }
    }

    override fun execute() {

//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class WakeUpPhase(
    private val gameName: String,
    private val communicationService: CommunicationService,
    private val allPlayers: List<Player>
) : Phase {
    override fun sendStartPhaseCommand() {
        communicationService.sendGameCommand(gameName, GameCommand.PHASE_WAKEUP)
    }

    override fun execute() {
        val dyingPlayers = allPlayers.filter { it.playerState == PlayerState.DYING }
        communicationService.communicate(gameName, WakeUpOutboundMessage(dyingPlayers), InboundType.ACK, allPlayers)
        dyingPlayers.forEach { it.playerState = PlayerState.DEAD }
    }

    override fun isActive(): Boolean {
        return true
    }

}

class DayPhase(
    private val gameName: String,
    private val communicationService: CommunicationService,
    private val allPlayers: List<Player>
) : Phase {
    override fun sendStartPhaseCommand() {
        communicationService.sendGameCommand(gameName, GameCommand.PHASE_DAY)
    }

    override fun execute() {
        // TODO: Let all alive players vote on who to kill
    }

    override fun isActive(): Boolean {
        return true
    }
}