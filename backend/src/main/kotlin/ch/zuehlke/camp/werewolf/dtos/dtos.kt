package ch.zuehlke.camp.werewolf.dtos

import sun.jvm.hotspot.utilities.BitMap

data class Player(val profile: Profile, var state: State, val role: Role)

data class Profile(val name: String, var avatar: BitMap)

data class Game(
    val name: String,
    val players: List<Player> = listOf(),
    val phases: Set<Phase> = setOf(),
    var currentPhase: Phase
)

data class Phase(val name: String, val voting: Voting)

data class Voting(
    val votes: List<Vote> = listOf(),
    val votedPlayers: List<Player> = listOf()
)

data class Vote(val voteOf: Player, val voteFor: List<Player>)

enum class State {
    DEAD, ASLEEP, AWAKE
}

enum class Role {
    WEREWOLF, VILLAGER
}