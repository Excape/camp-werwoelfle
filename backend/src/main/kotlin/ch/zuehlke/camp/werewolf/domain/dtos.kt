package ch.zuehlke.camp.werewolf.domain
import kotlinx.serialization.Serializable
import javax.persistence.*

@Serializable
data class Player(val identity: Identity){
    var playerState: PlayerState? = null
    var role: Role? = null
}

@Embeddable
@Serializable
data class Identity(val name: String) {
    // do not remove this, used by hibernate as default constructor
    private constructor() : this("")
}

@Entity
@Table
data class Profile(@Embedded val identity: Identity,
                   var password_plain: String?,
                   var password_encrypted: ByteArray?,
                   var salt: ByteArray?,
                   @Id @GeneratedValue(strategy = GenerationType.AUTO)
                   val id: Long = -1){
    // do not remove this, used by hibernate as default constructor
    private constructor() : this(Identity(""), "", ByteArray(0), ByteArray(0))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Profile

        if (identity.name != other.identity.name) return false
        if (password_plain != other.password_plain) return false
        if (password_encrypted?.zip(other.password_encrypted ?:ByteArray(0))?.map { (b1,b2) -> b1.toInt() == b2.toInt() }?.contains(false) == true) return false
        if (salt?.zip(other.salt ?:ByteArray(0))?.map { (b1,b2) -> b1.toInt() == b2.toInt() }?.contains(false) == true) return false

        return true
    }

    override fun hashCode(): Int {
        var result = identity.name.hashCode()
        result = 31 * result + 17 * password_plain.hashCode() + 7 * password_encrypted.hashCode() + salt.hashCode()
        return result
    }
}

data class Game(
    val name: String,
    val players: MutableList<Player>,
    val phases: List<Phase>
) {
    // TODO: add condition for victory of armor couple
    fun isGameOver(): Boolean {
        val numberOfVillagers = players.filter { player -> player.role == Role.VILLAGER }.count()
        val numberOfDeadVillagers = players.filter { player -> player.role == Role.VILLAGER && player.playerState == PlayerState.DEAD }.count()
        val numberOfWerewolves = players.filter { player -> player.role == Role.WEREWOLF }.count()
        val numberOfDeadWerewolves = players.filter { player -> player.role == Role.WEREWOLF && player.playerState == PlayerState.DEAD }.count()

        val allVillagersDead = numberOfDeadVillagers >= numberOfVillagers;
        val allWerewolvesDead = numberOfDeadWerewolves >= numberOfWerewolves;

        return allVillagersDead || allWerewolvesDead;
    }
}

@Serializable
data class Voting(
    val votes: List<Vote> = mutableListOf(),
    val votedPlayers: List<Player> = mutableListOf()
)

@Serializable
data class Vote(val voteOf: Player, val voteFor: List<Player>)

enum class PlayerState {
    ALIVE, DYING, DEAD
}

enum class Role {
    WEREWOLF, VILLAGER
}