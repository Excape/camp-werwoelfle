package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.*
import org.springframework.stereotype.Service

@Service
class GameFactory(
    private val roleService: RoleService,
    private val communicationService: CommunicationService,
    private val votingService: VotingService
) {
    fun createGame(
        gameName: String,
        players: MutableList<Player>
    ): Game {
        val settings = GameSettings(mutableMapOf())
        return Game(GameState.CREATED, gameName, players, initPhases(gameName, players, settings), settings)
    }

    private fun initPhases(
        gameName: String,
        allPlayers: List<Player>,
        settings: GameSettings
    ): List<Phase> {
        return listOf(
            RolePhase(gameName, roleService, communicationService, allPlayers, settings),
            NightfallPhase(gameName, communicationService, allPlayers),
            WerewolfPhase(gameName, communicationService, votingService, allPlayers),
            WakeUpPhase(gameName, communicationService, allPlayers),
            DayPhase(gameName, communicationService, votingService, allPlayers),
            ExecutionPhase(gameName, communicationService, allPlayers),
            GameOverPhase(gameName, communicationService, allPlayers)
        )
        // TODO: add new Phases here!

    }
}