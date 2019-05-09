package ch.zuehlke.camp.werewolf.domain

class Game(
    val name: String,
    val players: MutableList<Player>,
    private val phases: List<Phase>
) {
    fun run() {
        while (!isGameOver()) {
            phases.forEach { phase ->
                if (phase.isActive()) {
                    phase.sendStartPhaseCommand()
                    phase.execute()
                }
            }
        }
    }
    // TODO: add condition for victory of armor couple
    private fun isGameOver(): Boolean {
        val numberOfVillagers = players.filter { player -> player.role == Role.VILLAGER }.count()
        val numberOfDeadVillagers = players.filter { player -> player.role == Role.VILLAGER && player.playerState == PlayerState.DEAD }.count()
        val numberOfWerewolves = players.filter { player -> player.role == Role.WEREWOLF }.count()
        val numberOfDeadWerewolves = players.filter { player -> player.role == Role.WEREWOLF && player.playerState == PlayerState.DEAD }.count()

        val allVillagersDead = numberOfDeadVillagers >= numberOfVillagers;
        val allWerewolvesDead = numberOfDeadWerewolves >= numberOfWerewolves;

        return allVillagersDead || allWerewolvesDead;
    }
}