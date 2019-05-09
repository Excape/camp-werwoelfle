package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.eclipse.paho.client.mqttv3.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

typealias MessageCallback = (topic: String, message: MqttMessage) -> Unit

@Service
class MessageService(
    @Value("\${mqtt.broker.uri}") private val brokerURI: String,
    @Value("\${mqtt.broker.username}") private val brokerUsername: String,
    @Value("\${mqtt.broker.password}") private val brokerPassword: String
) {

    private val publisher: IMqttClient

    init {
        val publisherId = UUID.randomUUID().toString()
        publisher = MqttClient(brokerURI, publisherId)
        val options = configureConnection()
        publisher.connect(options)
    }

    @Throws(MqttException::class)
    private fun configureConnection(): MqttConnectOptions {
        val options = MqttConnectOptions()
        options.isAutomaticReconnect = true
        options.isCleanSession = true
        options.connectionTimeout = 10
        options.userName = brokerUsername
        options.password = brokerPassword.toCharArray()
        return options
    }

    @Throws(MqttException::class)
    fun publishMessage(message: MqttMessage) {
        message.qos = 0
        message.isRetained = true
        publisher.publish("HELLO", message)
    }

    @Throws(MqttException::class)
    fun publishToGame(gameName: String, command: GameCommand) {
        val mqttMessage = MqttMessage(command.name.toByteArray())
        configureMessage(mqttMessage)
        publisher.publish(gameName, mqttMessage)
    }

    fun publishToPlayer(
        player: Player,
        gameName: String,
        outboundMessage: OutboundMessage
    ) {
        val mqttMessage = MqttMessage(Json.stringify(outboundMessage.serializer as KSerializer<OutboundMessage>, outboundMessage).toByteArray())
        configureMessage(mqttMessage)
        publisher.publish(player.toTopic(gameName), mqttMessage)
    }

    fun subscribeToPlayers(
        gameName: String,
        inboundType: InboundType,
        players: List<Player>,
        handleMessage: MessageCallback
    ) {
        players.forEach { player ->
            publisher.subscribe("${player.toTopic(gameName)}/$inboundType", handleMessage)
        }

    }

    private fun configureMessage(mqttMessage: MqttMessage) {
        mqttMessage.qos = 0
        mqttMessage.isRetained = false
    }
}
