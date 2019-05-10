package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.*
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class RoleServiceTest {
    private val roleService: RoleService = RoleService()

    @Test
    fun generateRoles_noSettings_correctAmountOfWerewolves() {
        val game = getDefaultGame()

        val gameWithRoles = roleService.generateRolesInGame(game)
        val playersAsWerewolves = gameWithRoles.players.filter { player -> player.role == Role.WEREWOLF }
        val playersAsVillagers = gameWithRoles.players.filter { player -> player.role == Role.VILLAGER }

        assertEquals(playersAsWerewolves.size, 2)
        assertEquals(playersAsVillagers.size, 4)
    }

    @Test
    fun generateRoles_oneWerewolfSetting_correctAmountOfWerewolves() {
        val game = getDefaultGame()

        val roleMap = mutableMapOf<Role, Int>()
        roleMap.put(Role.WEREWOLF, 1)
        game.settings = GameSettings(roleMap)

        val gameWithRoles = roleService.generateRolesInGame(game)
        val playersAsWerewolves = gameWithRoles.players.filter { player -> player.role == Role.WEREWOLF }
        val playersAsVillagers = gameWithRoles.players.filter { player -> player.role == Role.VILLAGER }

        assertEquals(playersAsWerewolves.size, 1)
        assertEquals(playersAsVillagers.size, 5)
    }

    @Test
    fun generateRoles_notEnoughRoles_fillWithVillagers() {
        val game = getDefaultGame()

        val roleMap = mutableMapOf<Role, Int>()
        roleMap.put(Role.WEREWOLF, 2)
        roleMap.put(Role.VILLAGER,1)
        game.settings = GameSettings(roleMap)

        val gameWithRoles = roleService.generateRolesInGame(game)
        val playersAsWerewolves = gameWithRoles.players.filter { player -> player.role == Role.WEREWOLF }
        val playersAsVillagers = gameWithRoles.players.filter { player -> player.role == Role.VILLAGER }

        assertEquals(playersAsWerewolves.size, 2)
        assertEquals(playersAsVillagers.size, 4)
    }

    private fun getDefaultGame(): Game {
        val game = Game(
            GameState.CREATED, "Game", mutableListOf(
                Player(Identity("Alina")),
                Player(Identity("Reto")),
                Player(Identity("Andres")),
                Player(Identity("Muriele")),
                Player(Identity("Andres")),
                Player(Identity("Stefan"))
            ), emptyList(),
            GameSettings(emptyMap())
        )
        return game
    }

}