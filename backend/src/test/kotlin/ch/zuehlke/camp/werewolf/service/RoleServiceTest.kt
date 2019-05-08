package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.Game
import ch.zuehlke.camp.werewolf.domain.Player
import ch.zuehlke.camp.werewolf.domain.Profile
import ch.zuehlke.camp.werewolf.domain.Role
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class RoleServiceTest {
    private val roleService: RoleService = RoleService()

    @Test
    fun getCorrectAmountOfWerewolves() {
        val game = Game("Game", mutableListOf(
            Player(Profile("Andres", "1234", null, null)),
            Player(Profile("Remo", "1234", null, null)),
            Player(Profile("Robin", "1234", null, null)),
            Player(Profile("Alina", "1234", null, null)),
            Player(Profile("Muriele", "1234", null, null)),
            Player(Profile("Stefan", "1234", null, null))))
        val gameWithRoles = roleService.generateRolesInGame(game)
        val playersAsWerwolves = gameWithRoles.players.filter { player -> player.role == Role.WEREWOLF }
        assertEquals(playersAsWerwolves.size, 2)
    }

}