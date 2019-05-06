package ch.zuehlke.camp.werewolf.controllers

import ch.zuehlke.camp.werewolf.service.MessageService
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.Charset
import java.util.concurrent.CountDownLatch

@RestController
@RequestMapping("/api/v1/test")
class TestController(val messageService: MessageService) {

    @GetMapping
    fun getGreeting() : ResponseEntity<String> {
        messageService.publishMessage(MqttMessage("Hello Werewolf".toByteArray()))
        val receivedSignal = CountDownLatch(1)
        var payload = ""
        messageService.subscribe { topic: String, message: MqttMessage ->
            payload = message.payload.toString(Charset.forName("UTF-8"))
            receivedSignal.countDown();
        }
        receivedSignal.await()
        return ResponseEntity(payload, HttpStatus.OK)
    }


}