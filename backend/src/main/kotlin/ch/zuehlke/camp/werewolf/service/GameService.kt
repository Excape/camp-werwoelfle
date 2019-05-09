package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.Game
import ch.zuehlke.camp.werewolf.domain.GameCommand
import ch.zuehlke.camp.werewolf.domain.GameState
import org.springframework.stereotype.Service

@Service
class GameService(val communicationService: CommunicationService) {

    fun runGame(game: Game) {
        // inform players that game is starting
        communicationService.sendGameCommand(game.name, GameCommand.START)
        game.state = GameState.RUNNING
        game.run()
        endGame(game)
    }

    private fun endGame(game: Game) {
        game.state = GameState.FINISHED
        // TODO: handle end game
    }
}