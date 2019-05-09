package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.Game
import ch.zuehlke.camp.werewolf.domain.GameCommand
import org.springframework.stereotype.Service

@Service
class GameService(val communicationService: CommunicationService, val lobbyService: LobbyService) {

    fun runGame(game: Game) {
        // inform players that game is starting
        communicationService.sendGameCommand(game.name, GameCommand.START)
        game.isRunning = true
        game.run()
        endGame(game)
    }

    private fun endGame(game: Game) {
       lobbyService.games.remove(game)
        // TODO: handle end game
    }
}