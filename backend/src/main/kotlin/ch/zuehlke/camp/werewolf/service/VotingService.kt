package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.*
import org.springframework.stereotype.Service

@Service
class VotingService(private val communicationService: CommunicationService) {

    fun startVoting(gameName: String, voting: Voting): VotingResult {
        val outboundMessage = VotingOutboundMessage(voting)
        val voteMessages = communicationService.communicate(gameName, outboundMessage, InboundType.VOTE, voting.voters)
        val votes = extractVotes(voteMessages)

        val electedPlayers = voting.calculateElection(votes)
        return VotingResult(electedPlayers)
    }

    private fun extractVotes(voteMessages: List<InboundMessage>) =
        voteMessages
            .map { it as VoteInboundMessage }
            .map { it.vote }
}