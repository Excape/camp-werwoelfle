package ch.zuehlke.camp.werewolf.domain

import ch.zuehlke.camp.werewolf.service.CommunicationService
import ch.zuehlke.camp.werewolf.service.MessageService
import ch.zuehlke.camp.werewolf.service.RoleService

interface Phase {
    /**
     * Returns dying players
     */
    fun start(gameName: String): List<Player>

    fun isActive(): Boolean
    fun getCommand(): GameCommand
}

class RolePhase(
    private val roleService: RoleService,
    private val communicationService: CommunicationService,
    private val allPlayers: List<Player>
) : Phase {
    override fun getCommand(): GameCommand {
        return GameCommand.PHASE_ROLE
    }

    private var alreadyRun = false

    override fun isActive(): Boolean {
        return !alreadyRun
    }

    override fun start(gameName: String): List<Player> {
        roleService.generateRoles(allPlayers)
        val associateBy = allPlayers.associateBy({ it }, { RoleOutboundMessage(it.role!!) })
        communicationService.communicate(gameName, InboundType.ACK, associateBy)
        alreadyRun = true
        return listOf() // no dying players
    }
}

class WerewolfPhase(
    val roleService: RoleService,
    val messageService: MessageService,
    private val allPlayers: List<Player>
) : Phase {
    override fun getCommand(): GameCommand {
        return GameCommand.PHASE_WEREWOLF
    }

    override fun isActive(): Boolean {
        return allPlayers.filter {
            it.role == Role.WEREWOLF
        }.any {
            it.state != State.DEAD
        }
    }

    override fun start(gameName: String): List<Player> {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class WakeUpPhase(private val allPlayers: List<Player>) : Phase {
    override fun start(gameName: String): List<Player> {
        return emptyList()
    }

    override fun isActive(): Boolean {
        return true
    }

    override fun getCommand(): GameCommand {
        return GameCommand.PHASE_WAKEUP
    }

}

class DayPhase(private val allPlayers: List<Player>) : Phase {
    override fun getCommand(): GameCommand {
        return GameCommand.PHASE_DAY
    }

    override fun isActive(): Boolean {
        return true
    }

    override fun start(gameName: String): List<Player> {
        return listOf()
    }

}