package ch.zuehlke.camp.werewolf.domain

class Game(
    var state: GameState,
    val name: String,
    val players: MutableList<Player>,
    private val phases: List<Phase>,
    var settings: GameSettings
) {

    fun run() {
        do {
            phases.forEach { phase ->
                if (phase.isActive()) {
                    phase.sendStartPhaseCommand()
                    phase.execute()
                }
            }
        } while (!players.isGameOver())
    }
}
