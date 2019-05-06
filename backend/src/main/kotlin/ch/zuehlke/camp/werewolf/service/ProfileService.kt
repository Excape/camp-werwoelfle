package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.dtos.Profile
import ch.zuehlke.camp.werewolf.repository.ProfileRepository
import org.hibernate.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProfileService {

    @Autowired(required = true)
    lateinit var repository: ProfileRepository

    @Throws(ObjectNotFoundException::class)
    fun getProfile(name: String): Profile? {
        val profiles = repository.findByName(name)
        if (profiles.count() == 0) {
            return null
        }

        return profiles.first()
    }

    fun createProfile(profile: Profile): Profile? {
        val dbProfile = getProfile(profile.name)
        return if (dbProfile == null) {
            repository.save(profile)
            getProfile(profile.name)
        } else {
            null
        }

    }

    fun login(profile: Profile): Boolean {
        val profiles = repository.findByName(profile.name)
        if (profiles.count() != 1) {
            return false
        }

        return profile == profiles.first()
    }
}