package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.Game
import ch.zuehlke.camp.werewolf.domain.GameCommand

class GameService(val game: Game, val roleService: RoleService, val messageService: MessageService) {
    fun startGame() {
        roleService.generateRolesInGame(game)
        messageService.publishToGame(game, GameCommand.START)
        waitForPlayers()
    }

    private fun waitForPlayers() {
        TODO("Subscribe to players and wait until everyone gives OK") //To change body of created functions use File | Settings | File Templates.
    }
}