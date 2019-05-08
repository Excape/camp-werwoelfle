package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.*
import ch.zuehlke.camp.werewolf.domain.InboundType.*
import kotlinx.serialization.json.Json
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.springframework.stereotype.Service
import java.util.concurrent.CountDownLatch

@Service
class CommunicationService(private val messageService: MessageService) {
    fun communicate(
        gameName: String,
        outboundMessage: OutboundMessage,
        inboundType: InboundType,
        players: List<Player>
    ): List<InboundMessage> {

        val messageByPlayer = players.associateBy({ it }, { outboundMessage })

        return communicate(gameName, inboundType, messageByPlayer)
    }

    fun communicate(
        gameName: String,
        inboundType: InboundType,
        messageByPlayer: Map<Player, OutboundMessage>
    ): List<InboundMessage> {
        val countDownLatch = CountDownLatch(messageByPlayer.size)
        val inboundMessages = mutableListOf<InboundMessage>()

        val players = messageByPlayer.keys.toList()
        subscribeToPlayers(gameName, inboundType, players, inboundMessages, countDownLatch)

        publishMessages(gameName, messageByPlayer)

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
        gameName: String,
        messageByPlayer: Map<Player, OutboundMessage>
    ) {
        messageByPlayer.forEach { (player, message) ->
            messageService.publishToPlayer(
                player,
                gameName,
                message
            )
        }
    }

    private fun extractInboundType(topic: String): InboundType = valueOf(topic.substringAfterLast("/"))
}