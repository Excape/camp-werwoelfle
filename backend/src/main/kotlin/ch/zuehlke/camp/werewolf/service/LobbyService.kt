package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.Game
import ch.zuehlke.camp.werewolf.domain.Player
import ch.zuehlke.camp.werewolf.domain.Profile
import org.springframework.stereotype.Service

@Service
class LobbyService(val gameService: GameService, val gameFactory: GameFactory) {

    val games: MutableList<Game> = mutableListOf()

    // TODO: maybe we dont need this :-)
    val runningGames: MutableMap<String, Game> = mutableMapOf()

    fun createGame(name: String, profile: Profile): Game {
        val newGame = gameFactory.createGame(name, mutableListOf(Player(profile.identity)))
        games.add(newGame)
        return newGame
    }

    fun joinGame(name: String, profile: Profile): Game {
        val game = findGame(name)
        game.players.add(Player(profile.identity))
        return game
    }

    fun startGame(gameName: String) {
        val game = findGame(gameName)
        runningGames[gameName] = game
        gameService.startGame(game)
    }

    private fun findGame(name: String): Game {
        return games.find { game -> game.name == name } ?: throw IllegalArgumentException("Game $name not found")
    }
}