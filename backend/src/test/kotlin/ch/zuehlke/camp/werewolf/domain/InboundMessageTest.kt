package ch.zuehlke.camp.werewolf.domain

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull

internal class InboundMessageTest {
    @Test
    fun serialize_deserialize() {
        val voteInboundMessage = VoteInboundMessage(Vote(testPlayer(1), listOf(testPlayer(2))))

        val json = Json.stringify(voteInboundMessage.serializer as KSerializer<InboundMessage>, voteInboundMessage)
        assertNotNull(json)
        println(json)
        val parsedMessage = Json.parse(voteInboundMessage.serializer as KSerializer<InboundMessage>, json)
        assertEquals(voteInboundMessage, parsedMessage)
    }

    private fun testPlayer(id: Long) = Player(Identity("Andres"))
}