package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.*
import org.springframework.stereotype.Service

@Service
class GameFactory(private val roleService: RoleService, private val communicationService: CommunicationService) {
    fun createGame(
        gameName: String,
        players: MutableList<Player>
    ): Game {
        return Game(gameName, players, initPhases(players))
    }

    private fun initPhases(allPlayers: List<Player>): List<Phase> {
        return listOf(
            RolePhase(roleService, communicationService, allPlayers),
            NightfallPhase(communicationService, allPlayers),
            WerewolfPhase(communicationService, allPlayers),
            WakeUpPhase(communicationService, allPlayers),
            DayPhase(communicationService, allPlayers)
        )
        // TODO: add new Phases here!

    }
}