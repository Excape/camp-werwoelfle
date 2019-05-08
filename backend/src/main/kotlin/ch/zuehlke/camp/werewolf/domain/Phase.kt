package ch.zuehlke.camp.werewolf.domain

import ch.zuehlke.camp.werewolf.service.MessageService
import ch.zuehlke.camp.werewolf.service.RoleService

interface Phase {
    /**
     * Returns dying players
     */
    fun start(gameName: String, allPlayers: List<Player>): List<Player>

    fun isActive(): Boolean
}

class RolePhase(val roleService: RoleService, val messageService: MessageService) : Phase {
    private var alreadyRun = false

    override fun isActive(): Boolean {
       return !alreadyRun
    }

    override fun start(gameName: String, allPlayers: List<Player>): List<Player> {
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

class WakeUpPhase {

}

class DayPhase(): Phase {
    override fun isActive(): Boolean {
       return true
    }

    override fun start(gameName: String, allPlayers: List<Player>): List<Player> {
        return listOf()
    }

}