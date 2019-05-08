package ch.zuehlke.camp.werewolf.domain
import kotlinx.serialization.Serializable

@Serializable
abstract class OutboundMessage(val type: String)  {

}

data class RoleOutboundMessage(val role: Role) : OutboundMessage(type = "ROLE") {


}

data class PhaseOutboundMessage(val phase: Phase) : OutboundMessage(type = "PHASE") {

}

data class VotingOutboundMessage(val voting: Voting) : OutboundMessage(type = "VOTING") {

}