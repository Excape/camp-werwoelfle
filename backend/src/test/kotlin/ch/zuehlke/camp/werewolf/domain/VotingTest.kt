package ch.zuehlke.camp.werewolf.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
internal class VotingTest {

    private val playerRobin = testPlayer("Robin")
    private val playerAndres = testPlayer("Andres")
    private val playerStefan = testPlayer("Stefan")
    private val playerMuriele = testPlayer("Muriele")

    @Test
    fun calculateElection() {

        val voting = Voting(
            listOf(playerRobin, playerAndres, playerMuriele),
            listOf(playerStefan, playerMuriele, playerAndres, playerRobin),
            votesPerPlayer = 1,
            numberOfSeats = 1
        )
        val votes = listOf(
            Vote(playerRobin, listOf(playerMuriele)),
            Vote(playerAndres, listOf(playerStefan)),
            Vote(playerMuriele, listOf(playerStefan))
        )

        val result = voting.calculateElection(votes)
        assertEquals(1, result.count())
        assertEquals(playerStefan, result.first())
    }

    @Test
    fun calculateElection_ArmorScenario() {
        val voting = Voting(
            listOf(playerRobin),
            listOf(playerStefan, playerAndres, playerMuriele),
            votesPerPlayer = 2,
            numberOfSeats = 2
        )

        val votes = listOf(
            Vote(playerRobin, listOf(playerAndres, playerStefan))
        )

        val result = voting.calculateElection(votes)
        assertEquals(2, result.count())
        assertEquals(setOf(playerAndres, playerStefan), result)
    }

    private fun testPlayer(name: String) = Player(Identity(name))
}