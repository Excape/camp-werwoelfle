package ch.zuehlke.camp.werewolf.controllers

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(TestController::class)
class TestControllerTest {

    @Autowired
    private val mockMvc: MockMvc? = null

    @Test
    fun testHelloController() {
        this.mockMvc!!.perform(get("/api/v1"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").value("Hello Camp"))
    }

}