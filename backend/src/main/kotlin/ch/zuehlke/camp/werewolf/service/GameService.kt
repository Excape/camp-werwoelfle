package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.Game
import ch.zuehlke.camp.werewolf.domain.GameCommand
import org.springframework.stereotype.Service

@Service
class GameService(val messageService: MessageService) {

    fun runGame(game: Game) {
        // inform players that game is starting
        sendGameCommand(game, GameCommand.START)

        while (!game.isGameOver()) {
            game.phases.forEach { phase ->
                if (phase.isActive()) {
                    sendGameCommand(game, phase.getPhaseStartCommand())
                    phase.execute(game.name)
                }
            }
        }
        gameOver()
    }

    private fun gameOver() {
        TODO("not implemented")
    }

    private fun sendGameCommand(game: Game, gameCommand: GameCommand) {
        messageService.publishToGame(game, gameCommand)
    }
}