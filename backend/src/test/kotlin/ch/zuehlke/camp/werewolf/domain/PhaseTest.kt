package ch.zuehlke.camp.werewolf.domain

import ch.zuehlke.camp.werewolf.service.MessageService
import ch.zuehlke.camp.werewolf.service.RoleService
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration

@EnableAutoConfiguration
@RunWith(MockitoJUnitRunner::class)
class PhaseTest() {

    @Mock
    lateinit var messageService: MessageService

    @Mock
    lateinit var roleService: RoleService

    @Test
    fun werewolfPhase_awakeWerewolf_isActive() {
        val werewolf = Player(Profile("Wolfgang", null, null, null, 1));
        werewolf.role = Role.WEREWOLF
        werewolf.state = State.AWAKE
        val players = listOf(werewolf)

        val werewolfPhase = WerewolfPhase(roleService, messageService, players)

        assertTrue(werewolfPhase.isActive())
    }

    @Test
    fun werewolfPhase_deadWerewolf_isNotActive() {
        val werewolf = Player(Profile("Wolfgang", null, null, null, 1));
        werewolf.role = Role.WEREWOLF
        werewolf.state = State.DEAD
        val players = listOf(werewolf)

        val werewolfPhase = WerewolfPhase(roleService, messageService, players)

        assertFalse(werewolfPhase.isActive())
    }

    @Test
    fun werewolfPhase_noPlayer_isNotActive() {
        val players = mutableListOf<Player>()

        val werewolfPhase = WerewolfPhase(roleService, messageService, players)

        assertFalse(werewolfPhase.isActive())
    }
}
