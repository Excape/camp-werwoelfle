package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.*
import kotlinx.serialization.json.Json
import org.eclipse.paho.client.mqttv3.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

typealias MessageCallback = (topic: String, message: MqttMessage) -> Unit

@Service
class MessageService(
    @Value("\${mqtt.broker.uri}") val brokerURI: String,
    @Value("\${mqtt.broker.username}") val brokerUsername: String,
    @Value("\${mqtt.broker.password}") val brokerPassword: String
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
    fun publishToGame(game: Game, command: GameCommand) {
        val mqttMessage = MqttMessage(command.name.toByteArray())
        configureMessage(mqttMessage)
        publisher.publish(game.toTopic(), mqttMessage)
    }

    fun publishToPlayer(player: Player, gameName: String, outboundMessage: OutboundMessage) {
        val mqttMessage = MqttMessage(Json.stringify(OutboundMessage.serializer(), outboundMessage).toByteArray())
        configureMessage(mqttMessage)
        publisher.publish(player.toTopic(gameName), mqttMessage)
    }

    fun subscribeToPlayersInGame(
        game: Game,
        handleMessage: MessageCallback
    ) {
        game.players.forEach { player ->
            publisher.subscribe(player.toTopic(game), handleMessage)
        }

    }

    private fun configureMessage(mqttMessage: MqttMessage) {
        mqttMessage.qos = 0
        mqttMessage.isRetained = false
    }
}
