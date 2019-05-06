package ch.zuehlke.camp.werewolf.dtos
import javax.persistence.*


data class Player(val profile: Profile){
    var state: State? = null
    val role: Role? = null

}

@Entity
@Table
data class Profile(val name: String,
                   val password: String,
                   @Id @GeneratedValue(strategy = GenerationType.AUTO)
                   val id: Long = -1){
    // do not remove this, used by hibernate as default constructor
    private constructor() : this("", "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Profile

        if (name != other.name) return false
        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + password.hashCode()
        return result
    }
}

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