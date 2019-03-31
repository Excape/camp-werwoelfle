package ch.zuehlke.camp.werewolf.controllers

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(value = [TestController::class])
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