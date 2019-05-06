package ch.zuehlke.camp.werewolf.service

import org.eclipse.paho.client.mqttv3.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import java.util.UUID

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
    fun subscribe(callback: (String, MqttMessage) -> Unit) {
        publisher.subscribe("HELLO", callback)
    }

    @Throws(MqttException::class)
    fun publishMessage(message: MqttMessage) {
        message.qos = 0
        message.isRetained = true
        publisher.publish("HELLO", message)
    }
}
