package ch.zuehlke.camp.werewolf.service

import ch.zuehlke.camp.werewolf.domain.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
@Disabled("Manual test only (blocking)")
internal class CommunicationServiceTest {

    @Autowired
    lateinit var communicationService: CommunicationService

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun communicate() {
        val result =
            communicationService.communicate(
                "testGame",
                RoleOutboundMessage(Role.VILLAGER),
                InboundType.VOTE,
                listOf(Player(Identity("Marvin")))
            )
        println(result)
        assertEquals(1, result.size)
    }
}