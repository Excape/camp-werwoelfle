package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.*
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class RoleServiceTest {
    private val roleService: RoleService = RoleService()

    @Test
    fun getCorrectAmountOfWerewolves() {
        val game = Game(
            GameState.CREATED, "Game", mutableListOf(
                Player(Identity("Alina")),
                Player(Identity("Reto")),
                Player(Identity("Andres")),
                Player(Identity("Muriele")),
                Player(Identity("Andres")),
                Player(Identity("Stefan"))
            ), emptyList()
        )
        val gameWithRoles = roleService.generateRolesInGame(game)
        val playersAsWerwolves = gameWithRoles.players.filter { player -> player.role == Role.WEREWOLF }
        assertEquals(playersAsWerwolves.size, 2)
    }

}