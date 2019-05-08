package ch.zuehlke.camp.werewolf.domain

import ch.zuehlke.camp.werewolf.service.MessageService
import ch.zuehlke.camp.werewolf.service.RoleService

interface Phase {
    /**
     * Returns dying players
     */
    fun start(gameName: String): List<Player>

    fun isActive(): Boolean
}

class RolePhase(val roleService: RoleService, val messageService: MessageService, private val allPlayers: List<Player>) : Phase {
    private var alreadyRun = false

    override fun isActive(): Boolean {
       return !alreadyRun
    }

    override fun start(gameName: String): List<Player> {
        roleService.generateRoles(allPlayers)
        allPlayers.forEach { player ->
            messageService.publishToPlayer(
                player,
                gameName,
                RoleOutboundMessage(player.role!!)
            )
        }
        alreadyRun = true
        return listOf()
    }

}

class WerewolfPhase(val roleService: RoleService, val messageService: MessageService, private val allPlayers: List<Player>) : Phase {
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

class WakeUpPhase(private val allPlayers: List<Player>) {

}

class DayPhase(private val allPlayers: List<Player>): Phase {
    override fun isActive(): Boolean {
       return true
    }

    override fun start(gameName: String): List<Player> {
        return listOf()
    }

}