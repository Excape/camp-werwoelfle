package ch.zuehlke.camp.werewolf.controllers

import ch.zuehlke.camp.werewolf.domain.Picture
import ch.zuehlke.camp.werewolf.domain.Profile
import ch.zuehlke.camp.werewolf.service.DBFileService
import ch.zuehlke.camp.werewolf.service.ProfileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/profile")
class PlayerProfileController {

    @Autowired(required = true)
    lateinit var profileService: ProfileService
    @Autowired(required = true)
    lateinit var dbFileService: DBFileService


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


    @CrossOrigin
    @PostMapping("/picture")
    fun uploadPicture(
        @RequestParam(value = "file", required = false) file: MultipartFile,
        @RequestParam(value = "name", required = false) name: String
    ): ResponseEntity<HttpStatus> {
        val picture = this.dbFileService.storeFile(file,name)

        if (file.isEmpty) {
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }

        if (name.isEmpty()) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/picture")
    fun getPicture(@RequestParam profileName:String):ResponseEntity<Picture>{
        return ResponseEntity(this.dbFileService.getFileFor(profileName),HttpStatus.OK)
    }

    @PostMapping("")
    fun create(@RequestBody profileBody: Profile): Any {
        val profile = profileService.createProfile(profileBody)

        return profile ?: ResponseEntity<HttpStatus>(HttpStatus.NOT_MODIFIED)

    }
}