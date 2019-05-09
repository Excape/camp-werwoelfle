package ch.zuehlke.camp.werewolf.domain

class Game(
    var state: GameState,
    val name: String,
    val players: MutableList<Player>,
    private val phases: List<Phase>
) {

    fun run() {
        do {
            phases.forEach { phase ->
                if (phase.isActive()) {
                    phase.sendStartPhaseCommand()
                    phase.execute()
                }
            }
        } while (!isGameOver())
    }

    // TODO: add condition for victory of amor couple
    private fun isGameOver(): Boolean {
        return players.allVillagesAreDead() || players.allWerewolvesAreDead()
    }
}
