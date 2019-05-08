package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.*

class GameService(val game: Game, val roleService: RoleService, val messageService: MessageService) {
    private val nightPhases: MutableList<Phase> = mutableListOf()
    private val wakeUpPhase: WakeUpPhase? = null // TODO
    private val dayPhase: Phase? = null // TODO

    fun startGame() {
        // inform players that game is starting
        messageService.publishToGame(game, GameCommand.START)

        initPhases(game.players)

        while (!isGameOver()) {
            val dyingPlayers: MutableSet<Player> = mutableSetOf()
            nightPhases.forEach {
                if (it.isActive()) {
                    dyingPlayers.addAll(it.start(game.name))
                }
            }
            // TODO wake-up phase activate
            // TODO day phase activate
        }

    }

    private fun isGameOver(): Boolean {
        return true
    }

    private fun initPhases(allPlayers: List<Player>) {
        nightPhases.add(RolePhase(roleService, messageService, allPlayers))
        // TODO: add new Phases here!

    }
}