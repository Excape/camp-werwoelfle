package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.Picture
import ch.zuehlke.camp.werewolf.repository.DBFileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile

import javax.transaction.Transactional
import java.io.IOException
import java.util.*

@Service
@Transactional
class DBFileService(@param:Autowired private val dbFileRepository: DBFileRepository, @param:Autowired private val profileService: ProfileService) {

    fun storeFile(file: MultipartFile, profileName: String): Picture {
        // Normalize file name
        val fileName = StringUtils.cleanPath(file.originalFilename!!)

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw IllegalArgumentException("Sorry! Filename contains invalid path sequence $fileName")
            }

            val dbFile = Picture(
                pictureName = fileName,
                contentType = Objects.requireNonNull<String>(file.contentType),
                data = file.bytes,
                profile = this.profileService.getProfile(profileName)
            )

            return dbFileRepository.save(dbFile)
        } catch (ex: IOException) {
            throw IllegalArgumentException("Could not store file $fileName. Please try again!", ex)
        }

    }

    fun getFileFor(profileName: String): Picture {
        return dbFileRepository.findFirstByProfileIdentityName(name = profileName)}
}
