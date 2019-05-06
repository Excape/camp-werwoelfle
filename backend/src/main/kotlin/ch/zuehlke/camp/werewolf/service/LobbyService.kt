package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.dtos.Game
import ch.zuehlke.camp.werewolf.dtos.Player
import ch.zuehlke.camp.werewolf.dtos.Profile
import org.springframework.stereotype.Service

@Service
class LobbyService {

    val games: MutableList<Game> = mutableListOf()

    fun createGame(name: String, profile: Profile) : Game {
        val newGame = Game(name, mutableListOf(Player(profile)))
        games.add(newGame)
        return newGame
    }

    fun joinGame(name: String, profile: Profile) : Game?{
        val game = games.find { game -> game.name == name }
        game?.players?.add(Player(profile))
        return game
    }
}