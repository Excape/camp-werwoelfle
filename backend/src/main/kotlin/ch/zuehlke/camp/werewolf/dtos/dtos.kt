package ch.zuehlke.camp.werewolf.dtos


data class Player(val profile: Profile){
    var state: State? = null
    val role: Role? = null
}

data class Profile(val name: String)

data class Game(
    val name: String,
    val players: MutableList<Player> = mutableListOf()
) {
    val phases: Set<Phase> = setOf()
    var currentPhase: Phase? = null
}

data class Phase(val name: String, val voting: Voting)

data class Voting(
    val votes: List<Vote> = mutableListOf(),
    val votedPlayers: List<Player> = mutableListOf()
)

data class Vote(val voteOf: Player, val voteFor: List<Player>)

enum class State {
    DEAD, ASLEEP, AWAKE
}

enum class Role {
    WEREWOLF, VILLAGER
}