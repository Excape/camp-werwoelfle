package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.Game
import ch.zuehlke.camp.werewolf.domain.GameCommand
import org.springframework.stereotype.Service

@Service
class GameService(val communicationService: CommunicationService) {

    fun runGame(game: Game) {
        // inform players that game is starting
        communicationService.sendGameCommand(game.name, GameCommand.START)
        game.run()
        gameOver()
    }

    private fun gameOver() {
       // TODO
    }
}