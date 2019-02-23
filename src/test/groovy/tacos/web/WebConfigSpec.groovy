package tacos.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import tacos.WebMvcTestSpecification
import tacos.data.IngredientRepository

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest()
class WebConfigSpec extends WebMvcTestSpecification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    IngredientRepository ingredientRepository

    def "should render home page"() {
        expect:
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("Welcome to...")))
    }
}
