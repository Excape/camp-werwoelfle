package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.*
import org.springframework.stereotype.Service

@Service
class GameFactory(private val roleService: RoleService, private val communicationService: CommunicationService) {
    fun createGame(
        gameName: String,
        players: MutableList<Player>
    ): Game {
        return Game(gameName, players, initNightPhases(players), WakeUpPhase(players), DayPhase(players))
    }

    private fun initNightPhases(allPlayers: List<Player>): List<Phase> {
        return listOf(RolePhase(roleService, communicationService, allPlayers))
        // TODO: add new Phases here!

    }
}