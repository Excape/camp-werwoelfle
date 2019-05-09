package ch.zuehlke.camp.werewolf.domain

import kotlinx.serialization.Serializable

@Serializable
data class Voting(
    val voters: List<Player>,
    val candidates: List<Player>,
    val votesPerPlayer: Int,
    val numberOfSeats: Int
) {
    fun calculateElection(votes: List<Vote>): List<Player> {
        val validVotes = votes.filter(this::isValidVote)

        val votingCount = countVotes(validVotes)

        return getElectedPlayers(votingCount).map { player ->
            candidates.find {it == player} ?: throw IllegalStateException("elected player is not a candidate")
        }
    }

    private fun getElectedPlayers(votingCount: Map<Player, Int>): Set<Player> {
        return votingCount
            .asSequence()
            .toList()
            .shuffled() // randomize in case there is a draw
            .sortedByDescending { (_, count) -> count }
            .take(numberOfSeats)
            .map { (player, _) -> player }
            .toSet()
    }

    private fun countVotes(validVotes: List<Vote>): Map<Player, Int> {
        return validVotes
            .flatMap { it.voteFor }
            .groupingBy { it }
            .eachCount()
    }

    private fun isValidVote(vote: Vote): Boolean {
        // TODO: Verify that each player only votes once
        return voters.contains(vote.voteOf) &&
                vote.voteFor.size == votesPerPlayer &&
                vote.voteFor.all { candidates.contains(it) }
    }
}

@Serializable
data class Vote(
    val voteOf: Player,
    val voteFor: List<Player>
)

data class VotingResult(
    val electedPlayers: List<Player>
)