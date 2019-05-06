package ch.zuehlke.camp.werewolf.controllers

import ch.zuehlke.camp.werewolf.dtos.Profile
import ch.zuehlke.camp.werewolf.service.ProfileService
import org.hibernate.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException




@RestController
@RequestMapping("/api/v1/profile")
class PlayerProfileController {

    @Autowired(required = true)
    lateinit var profileService: ProfileService

    @RequestMapping("/{name}")
    fun get(@PathVariable("name") name: String ): Profile {
        try {
            return profileService.getProfile(name = name)
        } catch (ex: ObjectNotFoundException) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND, "Profile Not Found", ex
            )
        }
    }

    @PostMapping("")
    fun save(@RequestBody profile: Profile): Profile {
        return profileService.saveProfile(profile)
    }

}