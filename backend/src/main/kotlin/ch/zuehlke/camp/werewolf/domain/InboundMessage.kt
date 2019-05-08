package ch.zuehlke.camp.werewolf.domain
import ch.zuehlke.camp.werewolf.domain.InboundType.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
abstract class InboundMessage(val type: InboundType, @Transient val serializer: KSerializer<out InboundMessage> = InboundMessage.serializer())  {

}

@Serializable
class AckInboundMessage() : InboundMessage(ACK, AckInboundMessage.serializer()) {
}

@Serializable
data class VoteInboundMessage(val vote: Vote) : InboundMessage(VOTE, VoteInboundMessage.serializer()) {
}

enum class InboundType {
    VOTE, ACK
}