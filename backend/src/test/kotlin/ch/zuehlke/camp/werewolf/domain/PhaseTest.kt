package ch.zuehlke.camp.werewolf.domain

import ch.zuehlke.camp.werewolf.service.CommunicationService
import ch.zuehlke.camp.werewolf.service.MessageService
import ch.zuehlke.camp.werewolf.service.RoleService
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.autoconfigure.EnableAutoConfiguration

@EnableAutoConfiguration
@RunWith(MockitoJUnitRunner::class)
class PhaseTest() {

    @Mock
    lateinit var messageService: MessageService

    @Mock
    lateinit var roleService: RoleService

    @Mock
    lateinit var communicationService: CommunicationService

    @Test
    fun werewolfPhase_awakeWerewolf_isActive() {
        val werewolf = Player(Identity("Stefan"))
        werewolf.role = Role.WEREWOLF
        werewolf.playerState = PlayerState.ALIVE
        val players = listOf(werewolf)

        val werewolfPhase = WerewolfPhase(communicationService, players)

        assertTrue(werewolfPhase.isActive())
    }

    @Test
    fun werewolfPhase_deadWerewolf_isNotActive() {
        val werewolf = Player(Identity("Stefanie"))
        werewolf.role = Role.WEREWOLF
        werewolf.playerState = PlayerState.DEAD
        val players = listOf(werewolf)

        val werewolfPhase = WerewolfPhase(communicationService, players)

        assertFalse(werewolfPhase.isActive())
    }

    @Test
    fun werewolfPhase_noPlayer_isNotActive() {
        val players = mutableListOf<Player>()

        val werewolfPhase = WerewolfPhase(communicationService, players)

        assertFalse(werewolfPhase.isActive())
    }
}

