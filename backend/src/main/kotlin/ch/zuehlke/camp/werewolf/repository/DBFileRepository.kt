package ch.zuehlke.camp.werewolf.repository

import ch.zuehlke.camp.werewolf.domain.Picture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface DBFileRepository : CrudRepository<Picture, Long> {
    fun findFirstByProfileIdentityName(name: String): Picture
}