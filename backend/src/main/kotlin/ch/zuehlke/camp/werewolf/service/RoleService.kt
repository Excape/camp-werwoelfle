package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.dtos.Game
import ch.zuehlke.camp.werewolf.dtos.Role

class RoleService {

    fun generateRolesInGame(game: Game): Game {
        val countPlayers = game.players.size
        val countWerewolf = countPlayers / 2 - 1
        game.players.shuffled().subList(0,countWerewolf).forEach {
            it.role = Role.WEREWOLF
        }
        return game
    }
}