package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.Game
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
}