package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.dtos.Game
import ch.zuehlke.camp.werewolf.dtos.Player
import ch.zuehlke.camp.werewolf.dtos.Profile
import ch.zuehlke.camp.werewolf.dtos.Role
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class RoleServiceTest {
    private val roleService: RoleService = RoleService()

    @Test
    fun getCorrectAmountOfWerewolves() {
        val game = Game("Game", mutableListOf<Player>(
            Player(Profile("Andres", "1234")),
            Player(Profile("Remo", "1234")),
            Player(Profile("Robin", "1234")),
            Player(Profile("Alina", "1234")),
            Player(Profile("Muriele", "1234")),
            Player(Profile("Stefan", "1234"))))
        val gameWithRoles = roleService.generateRolesInGame(game)
        val playersAsWerwolves = gameWithRoles.players.filter { player -> player.role == Role.WEREWOLF }
        assertEquals(playersAsWerwolves.size, 2)
    }

}