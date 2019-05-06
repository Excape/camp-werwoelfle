package ch.zuehlke.camp.werewolf.controllers

import ch.zuehlke.camp.werewolf.dtos.Profile
import ch.zuehlke.camp.werewolf.repository.ProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/profile")
class PlayerProfileController {

    @Autowired(required = true)
    lateinit var repository: ProfileRepository

    @RequestMapping("/save")
    fun save(): String {
        repository.save(Profile("Jack"))
        repository.save(Profile("Rose"))
        return "Done"
    }

}