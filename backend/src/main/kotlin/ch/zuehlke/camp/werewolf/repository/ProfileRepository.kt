package ch.zuehlke.camp.werewolf.repository

import ch.zuehlke.camp.werewolf.dtos.Profile
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository: CrudRepository<Profile, Long> {

}