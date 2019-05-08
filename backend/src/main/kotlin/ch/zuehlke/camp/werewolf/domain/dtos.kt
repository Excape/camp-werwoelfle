package ch.zuehlke.camp.werewolf.domain
import javax.persistence.*


data class Player(val profile: Profile){
    var state: State? = null
    var role: Role? = null

}

@Entity
@Table
data class Profile(val name: String,
                   var password_plain: String?,
                   var password_encrypted: ByteArray?,
                   var salt: ByteArray?,
                   @Id @GeneratedValue(strategy = GenerationType.AUTO)
                   val id: Long = -1){
    // do not remove this, used by hibernate as default constructor
    private constructor() : this("", "", ByteArray(0), ByteArray(0))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Profile

        if (name != other.name) return false

        if (password_plain != other.password_plain) return false
        if (password_encrypted?.zip(other.password_encrypted ?:ByteArray(0))?.map { (b1,b2) -> b1.toInt() == b2.toInt() }?.contains(false) == true) return false
        if (salt?.zip(other.salt ?:ByteArray(0))?.map { (b1,b2) -> b1.toInt() == b2.toInt() }?.contains(false) == true) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + 17 * password_plain.hashCode() + 7 * password_encrypted.hashCode() + salt.hashCode()
        return result
    }
}

data class Game(
    val name: String,
    val players: MutableList<Player> = mutableListOf()
)


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