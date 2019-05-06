package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.dtos.Game
import ch.zuehlke.camp.werewolf.dtos.Player
import ch.zuehlke.camp.werewolf.dtos.Profile
import org.springframework.stereotype.Service

@Service
class LobbyService {

    val games: MutableList<Game> = mutableListOf()

    fun createGame(name: String, profile: Profile) {
        games.add(Game(name, mutableListOf(Player(profile))))
    }

    fun joinGame(name: String, profile: Profile) {
        games.find { game -> game.name == name }
            ?.let { game -> game.players.add(Player(profile)) }
    }
}