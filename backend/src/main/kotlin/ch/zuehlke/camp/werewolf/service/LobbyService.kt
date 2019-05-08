package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.Game
import ch.zuehlke.camp.werewolf.domain.GameCommand
import ch.zuehlke.camp.werewolf.domain.Player
import ch.zuehlke.camp.werewolf.domain.Profile
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class LobbyService(val messageService: MessageService, val roleService: RoleService) {

    val games: MutableList<Game> = mutableListOf()

    val runningGames: MutableMap<String, GameService> = mutableMapOf()

    fun createGame(name: String, profile: Profile) : Game {
        val newGame = Game(name, mutableListOf(Player(profile.id)))
        games.add(newGame)
        return newGame
    }

    fun joinGame(name: String, profile: Profile) : Game{
        val game = findGame(name)
        game.players.add(Player(profile.id))
        return game
    }

    fun startGame(gameName: String) {
        val game = findGame(gameName)
        val gameService = GameService(game, roleService, messageService)
        runningGames[gameName] = gameService
        gameService.startGame()
    }

    private fun findGame(name: String): Game {
        val game = games.find { game -> game.name == name }
        if (game == null) {
            throw IllegalArgumentException("Game $name not found")
        }
        return game
    }
}