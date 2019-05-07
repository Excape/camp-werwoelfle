package ch.zuehlke.camp.werewolf.controllers

import ch.zuehlke.camp.werewolf.domain.Profile
import ch.zuehlke.camp.werewolf.service.ProfileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/profile")
class PlayerProfileController {

    @Autowired(required = true)
    lateinit var profileService: ProfileService

    @RequestMapping("/{name}")
    fun get(@PathVariable("name") name: String): Any {

        val profile = profileService.getProfile(name = name)

        return profile ?: ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/login")
    fun login(@RequestBody profile: Profile): ResponseEntity<HttpStatus> {
        val success = profileService.login(profile)
        return if (success) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("")
    fun create(@RequestBody profileBody: Profile): Any {
        val profile = profileService.createProfile(profileBody)

        return profile ?: ResponseEntity<HttpStatus>(HttpStatus.NOT_MODIFIED)

    }

}