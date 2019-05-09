package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.*
import org.springframework.stereotype.Service

@Service
class GameFactory(private val roleService: RoleService, private val communicationService: CommunicationService) {
    fun createGame(
        gameName: String,
        players: MutableList<Player>
    ): Game {
        return Game(GameState.CREATED,gameName, players, initPhases(gameName, players))
    }

    private fun initPhases(
        gameName: String,
        allPlayers: List<Player>
    ): List<Phase> {
        return listOf(
            RolePhase(gameName, roleService, communicationService, allPlayers),
            NightfallPhase(gameName, communicationService, allPlayers),
            WerewolfPhase(gameName, communicationService, allPlayers),
            WakeUpPhase(gameName, communicationService, allPlayers),
            DayPhase(gameName, communicationService, allPlayers)
        )
        // TODO: add new Phases here!

    }
}