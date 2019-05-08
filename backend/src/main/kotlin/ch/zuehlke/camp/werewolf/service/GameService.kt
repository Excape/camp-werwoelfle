package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.Game
import ch.zuehlke.camp.werewolf.domain.GameCommand
import ch.zuehlke.camp.werewolf.domain.Player
import org.springframework.stereotype.Service

@Service
class GameService(
    val messageService: MessageService
) {

    fun startGame(game: Game) {
        // inform players that game is starting
        messageService.publishToGame(game, GameCommand.START)

        while (!game.isGameOver()) {
            val dyingPlayers: MutableSet<Player> = mutableSetOf()
            game.nightPhases.forEach {
                if (it.isActive()) {
                    messageService.publishToGame(game, it.getCommand())
                    dyingPlayers.addAll(it.start(game.name))
                }
            }
            // TODO wake-up phase activate
            // TODO day phase activate
        }

    }
}