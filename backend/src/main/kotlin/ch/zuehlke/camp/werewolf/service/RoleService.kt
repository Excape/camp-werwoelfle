package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.Game
import ch.zuehlke.camp.werewolf.domain.GameSettings
import ch.zuehlke.camp.werewolf.domain.Player
import ch.zuehlke.camp.werewolf.domain.Role
import org.springframework.stereotype.Service

@Service
class RoleService {

    fun generateRolesInGame(game: Game): Game {
        generateRoles(game.players, game.settings);
        return game
    }

    fun generateRoles(allPlayers: List<Player>, settings: GameSettings) {
        val countPlayers = allPlayers.size
        var currentPlayer = 0
        allPlayers.shuffled()

        settings.roleToCount.forEach { role, count ->
            val remainingPlayers = countPlayers-currentPlayer
            val distributionCount = Math.min(remainingPlayers, count)
            allPlayers.subList(currentPlayer, currentPlayer + distributionCount).forEach{
                it.role = role
            }
            currentPlayer += distributionCount
        }

        if (!settings.roleToCount.containsKey(Role.WEREWOLF)) {
            val remainingPlayers = countPlayers-currentPlayer
            val countWerewolf = Math.max(remainingPlayers / 2 - 1, 0)
            allPlayers.subList(currentPlayer,currentPlayer+countWerewolf).forEach {
                it.role = Role.WEREWOLF
            }
            currentPlayer += countWerewolf
        }

        for (player in allPlayers) {
            if (player.role == null) {
                player.role = Role.VILLAGER
            }
        }
    }
}