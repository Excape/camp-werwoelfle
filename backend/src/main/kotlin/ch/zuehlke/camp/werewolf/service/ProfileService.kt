package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.dtos.Game
import ch.zuehlke.camp.werewolf.dtos.Player
import ch.zuehlke.camp.werewolf.dtos.Profile
import ch.zuehlke.camp.werewolf.repository.ProfileRepository
import org.hibernate.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class ProfileService {

    @Autowired(required = true)
    lateinit var repository: ProfileRepository

    @Throws(ObjectNotFoundException::class)
    fun getProfile(name: String): Profile {
        val profiles = repository.findByName(name)
        if (profiles.count() == 0) {
            throw ObjectNotFoundException(null, "Profile")
        }

        return profiles.first()
    }

    fun saveProfile(profile: Profile): Profile {
        repository.save(profile)
        return getProfile(profile.name)
    }
}