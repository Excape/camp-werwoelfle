package ch.zuehlke.camp.werewolf.repository

import ch.zuehlke.camp.werewolf.domain.Profile
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository: CrudRepository<Profile, Long> {

    fun findByName(name: String): Iterable<Profile>
}