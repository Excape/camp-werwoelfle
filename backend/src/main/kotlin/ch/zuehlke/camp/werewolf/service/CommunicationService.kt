package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.*
import ch.zuehlke.camp.werewolf.domain.InboundType.*
import kotlinx.serialization.json.Json
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.springframework.stereotype.Service
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger
import javax.transaction.NotSupportedException

@Service
class CommunicationService(private val messageService: MessageService) {
    fun communicate(
        gameName: String,
        outboundMessage: OutboundMessage,
        inboundType: InboundType,
        players: List<Player>
    ): List<InboundMessage> {

        val countDownLatch = CountDownLatch(players.size)
        val inboundMessages = mutableListOf<InboundMessage>()

        subscribeToPlayers(gameName, inboundType, players, inboundMessages, countDownLatch)

        publishMessages(players, gameName, outboundMessage)

        countDownLatch.await()
        return inboundMessages.toList()
    }

    private fun subscribeToPlayers(
        gameName: String,
        inboundType: InboundType,
        players: List<Player>,
        inboundMessages: MutableList<InboundMessage>,
        countDownLatch: CountDownLatch
    ) {
        messageService.subscribeToPlayers(
            gameName,
            inboundType,
            players
        ) { topic: String, mqttMessage: MqttMessage ->
            val parsedMessage = parseMessage(extractInboundType(topic), mqttMessage)
            inboundMessages.add(parsedMessage)
            println("received $parsedMessage")
            countDownLatch.countDown()
            println("countdown ${countDownLatch.count}")
        }
    }

    private fun parseMessage(inboundType: InboundType, mqttMessage: MqttMessage): InboundMessage {
        val jsonObject = String(mqttMessage.payload, Charsets.UTF_8)
        return when (inboundType) {
            VOTE -> Json.parse(VoteInboundMessage.serializer(), jsonObject)
            ACK -> Json.parse(AckInboundMessage.serializer(), jsonObject)
        }


    }

    private fun publishMessages(
        players: List<Player>,
        gameName: String,
        outboundMessage: OutboundMessage
    ) {
        players.forEach {
            messageService.publishToPlayer(
                it,
                gameName,
                outboundMessage
            )
        }
    }

    private fun extractInboundType(topic: String): InboundType = valueOf(topic.substringAfterLast("/"))
}