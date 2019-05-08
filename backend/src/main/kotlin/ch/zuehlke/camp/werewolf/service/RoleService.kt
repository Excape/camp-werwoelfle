package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.Game
import ch.zuehlke.camp.werewolf.domain.Player
import ch.zuehlke.camp.werewolf.domain.Role
import org.springframework.stereotype.Service

@Service
class RoleService {

    fun generateRolesInGame(game: Game): Game {
        val countPlayers = game.players.size
        val countWerewolf = countPlayers / 2 - 1
        game.players.shuffled().subList(0,countWerewolf).forEach {
            it.role = Role.WEREWOLF
        }
        return game
    }

    fun generateRoles(allPlayers: List<Player>) {
        val countPlayers = allPlayers.size
        val countWerewolf = countPlayers / 2 - 1
        allPlayers.shuffled().subList(0,countWerewolf).forEach {
            it.role = Role.WEREWOLF
        }
    }
}