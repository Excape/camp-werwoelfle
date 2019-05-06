package ch.zuehlke.camp.werewolf.controllers

import ch.zuehlke.camp.werewolf.dtos.Profile
import ch.zuehlke.camp.werewolf.repository.ProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class TestController {

    @GetMapping
    fun getTestString(): ResponseEntity<String> {
        return ResponseEntity("Hello Camp", HttpStatus.OK)
    }

}