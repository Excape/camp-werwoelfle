package ch.zuehlke.camp.werewolf.controllers

import ch.zuehlke.camp.werewolf.domain.Picture
import ch.zuehlke.camp.werewolf.domain.Profile
import ch.zuehlke.camp.werewolf.service.DBFileService
import ch.zuehlke.camp.werewolf.service.ProfileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.ResourceUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import javax.imageio.ImageIO

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
        val picture = this.dbFileService.storeFile(file, name)

        if (file.isEmpty) {
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }

        if (name.isEmpty()) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/picture/{name}")
    fun getPicture(@PathVariable name: String): ResponseEntity<ByteArray> {
        val file: Picture
        try {
            file = this.dbFileService.getFileFor(name)
            val responseHeaders = HttpHeaders()

            responseHeaders.set(
                HttpHeaders.CONTENT_TYPE, file.contentType
            )
            return ResponseEntity
                .ok()
                .headers(responseHeaders)
                .body(file.data)
        } catch (e: Exception) {
            val encodedImage = getEncodedDefaultImage()
            val defaultResponseHeaders = HttpHeaders()
            defaultResponseHeaders.set(
                HttpHeaders.CONTENT_TYPE, "image/jpeg"
            )
            return ResponseEntity
                .ok()
                .headers(defaultResponseHeaders)
                .body(encodedImage)
        }
    }

    private fun getEncodedDefaultImage(): ByteArray? {
        val file = ResourceUtils.getFile("classpath:profile.jpg")
        val image = ImageIO.read(file)
        val outputStream = ByteArrayOutputStream()
        ImageIO.write(image, "jpg", outputStream)
        val encodedPicture = outputStream.toByteArray()
        outputStream.close()
        return encodedPicture
    }

    @PostMapping("")
    fun create(@RequestBody profileBody: Profile): Any {
        val profile = profileService.createProfile(profileBody)
        return profile ?: ResponseEntity<HttpStatus>(HttpStatus.NOT_MODIFIED)
    }
}