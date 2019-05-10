package ch.zuehlke.camp.werewolf.domain

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import org.hibernate.annotations.Type
import javax.persistence.*
import javax.persistence.Lob


@Serializable
data class Player(val identity: Identity) {
    val checked = false
    var playerState: PlayerState? = PlayerState.ALIVE
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
data class Profile(
    @Embedded val identity: Identity,
    var password_plain: String?,
    var password_encrypted: ByteArray?,
    var salt: ByteArray?,
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = -1
) {
    // do not remove this, used by hibernate as default constructor
    private constructor() : this(Identity(""), "", ByteArray(0), ByteArray(0))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Profile

        if (identity.name != other.identity.name) return false
        if (password_plain != other.password_plain) return false
        if (password_encrypted?.zip(
                other.password_encrypted ?: ByteArray(0)
            )?.map { (b1, b2) -> b1.toInt() == b2.toInt() }?.contains(false) == true
        ) return false
        if (salt?.zip(
                other.salt ?: ByteArray(0)
            )?.map { (b1, b2) -> b1.toInt() == b2.toInt() }?.contains(false) == true
        ) return false

        return true
    }

    override fun hashCode(): Int {
        var result = identity.name.hashCode()
        result = 31 * result + 17 * password_plain.hashCode() + 7 * password_encrypted.hashCode() + salt.hashCode()
        return result
    }
}

@Entity
@Table
data class Picture(
    var pictureName: String? = null,
    var contentType: String? = null,
    @Type(type = "org.hibernate.type.BinaryType")
    var data: ByteArray? = null,
    @OneToOne
    var profile: Profile? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Picture

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}

enum class PlayerState {
    ALIVE, DYING, DEAD
}

enum class Role {
    WEREWOLF, VILLAGER
}

enum class GameState {
    RUNNING, FINISHED, CREATED
}