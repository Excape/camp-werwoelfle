package ch.zuehlke.camp.werewolf.controllers

import ch.zuehlke.camp.werewolf.services.PhaseService
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.Charset
import java.util.concurrent.CountDownLatch



@RestController
@RequestMapping("/api/v1/")
class TestController(@Autowired val phaseService: PhaseService) {

    @GetMapping
    fun getTestString(): ResponseEntity<String> {
        return ResponseEntity("Hello Camp", HttpStatus.OK)
    }

    @GetMapping("greeting")
    fun getGreeting() : ResponseEntity<String>{
        phaseService.publishMessage(MqttMessage("Hello Werewolf".toByteArray()))
        val receivedSignal = CountDownLatch(1)
        var payload = ""
        phaseService.subscribe { topic: String, message: MqttMessage ->
            payload = message.payload.toString(Charset.forName("UTF-8"))
            receivedSignal.countDown();
        }
        receivedSignal.await()
        return ResponseEntity(payload, HttpStatus.OK)
    }

}