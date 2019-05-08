package ch.zuehlke.camp.werewolf.domain

import ch.zuehlke.camp.werewolf.domain.OutboundType.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
abstract class OutboundMessage(val type: OutboundType, @Transient val serializer: KSerializer<out OutboundMessage> = OutboundMessage.serializer())


@Serializable
data class RoleOutboundMessage(val role: Role) : OutboundMessage(ROLE, RoleOutboundMessage.serializer())

@Serializable
data class VotingOutboundMessage(val voting: Voting) : OutboundMessage(VOTING, VotingOutboundMessage.serializer())

enum class OutboundType {
    ROLE, VOTING
}
