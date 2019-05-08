package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.Profile
import ch.zuehlke.camp.werewolf.repository.ProfileRepository
import org.hibernate.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


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
            secureProfile(profile, dbProfile?.salt)
            repository.save(profile)
            getProfile(profile.name)
        } else {
            null
        }

    }

    private fun secureProfile(profile: Profile, salt: ByteArray?) {
        if (profile.password_plain == null) {
            return
        }

        profile.salt = salt?:generateSalt()
        profile.password_encrypted = encode(profile.password_plain, profile.salt)
        profile.password_plain = null
    }

    private fun encode(password_plain: String?, salt: ByteArray?): ByteArray? {
        val nonNullSalt = salt?:ByteArray(0)
        val nonNullPasswordPlain = password_plain?:""
        val spec = PBEKeySpec(nonNullPasswordPlain.toCharArray(), nonNullSalt, 65536, 128)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        return factory.generateSecret(spec).encoded
    }

    private fun generateSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return salt
    }

    fun login(profile: Profile): Boolean {
        val profiles = repository.findByName(profile.name)
        if (profiles.count() != 1) {
            return false
        }

        secureProfile(profile, profiles.first().salt)

        return profile == profiles.first()
    }
}