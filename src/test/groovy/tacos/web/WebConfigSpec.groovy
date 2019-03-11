package tacos.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import tacos.MockedSpecification
import tacos.data.IngredientRepository
import tacos.data.UserRepository

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(secure = false)
class WebConfigSpec extends MockedSpecification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    IngredientRepository ingredientRepository

    @Autowired
    UserRepository userRepository

    def "should render home page"() {
        expect:
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("Welcome to...")))
    }

    def "should render login page"() {
        expect:
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(content().string(containsString("Username")))
    }
}
