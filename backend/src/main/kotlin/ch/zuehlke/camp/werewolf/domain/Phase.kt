package ch.zuehlke.camp.werewolf.domain

import ch.zuehlke.camp.werewolf.service.CommunicationService
import ch.zuehlke.camp.werewolf.service.MessageService
import ch.zuehlke.camp.werewolf.service.RoleService

interface Phase {
    fun execute(gameName: String)
    fun isActive(): Boolean
    fun getPhaseStartCommand(): GameCommand
}

class RolePhase(
    private val roleService: RoleService,
    private val communicationService: CommunicationService,
    private val allPlayers: List<Player>
) : Phase {
    override fun getPhaseStartCommand(): GameCommand {
        return GameCommand.PHASE_ROLE
    }

    private var alreadyRun = false

    override fun isActive(): Boolean {
        return !alreadyRun
    }

    override fun execute(gameName: String) {
        roleService.generateRoles(allPlayers)
        val associateBy = allPlayers.associateBy({ it }, { RoleOutboundMessage(it.role!!) })
        communicationService.communicate(gameName, InboundType.ACK, associateBy)
        alreadyRun = true
    }
}

class NightfallPhase(private val communicationService: CommunicationService,
                     private val allPlayers: List<Player>) : Phase {
    override fun execute(gameName: String) {
        // Inform players that it's night
    }

    override fun isActive(): Boolean {
        return true
    }

    override fun getPhaseStartCommand(): GameCommand {
        return GameCommand.PHASE_NIGHTFALL
    }

}

class WerewolfPhase(
    private val communicationService: CommunicationService,
    private val allPlayers: List<Player>
) : Phase {
    override fun getPhaseStartCommand(): GameCommand {
        return GameCommand.PHASE_WEREWOLF
    }

    override fun isActive(): Boolean {
        return allPlayers.filter {
            it.role == Role.WEREWOLF
        }.any {
            it.playerState != PlayerState.DEAD
        }
    }

    override fun execute(gameName: String) {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class WakeUpPhase(private val communicationService: CommunicationService,
                  private val allPlayers: List<Player>) : Phase {
    override fun execute(gameName: String) {
        //TODO: "Send all dying players to players")
        //TODO: "Kill all dying players")
    }

    override fun isActive(): Boolean {
        return true
    }

    override fun getPhaseStartCommand(): GameCommand {
        return GameCommand.PHASE_WAKEUP
    }

}

class DayPhase(private val communicationService: CommunicationService,
               private val allPlayers: List<Player>) : Phase {
    override fun execute(gameName: String) {
        // TODO: Let all alive players vote on whom to kill
    }

    override fun getPhaseStartCommand(): GameCommand {
        return GameCommand.PHASE_DAY
    }

    override fun isActive(): Boolean {
        return true
    }
}