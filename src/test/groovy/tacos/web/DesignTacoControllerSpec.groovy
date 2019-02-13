package tacos.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(DesignTacoController.class)
class DesignTacoControllerSpec extends Specification {
    @Autowired
    private MockMvc mockMvc

    def "should render design page"() {
        expect:
        mockMvc.perform(get("/design"))
                .andExpect(status().isOk())
                .andExpect(view().name("design"))
                .andExpect(content().string(containsString("Designate your wrap")))
                .andExpect(content().string(containsString("Flour Tortilla")))
                .andExpect(content().string(containsString("Corn Tortilla")))
                .andExpect(content().string(containsString("Pick your protein:")))
                .andExpect(content().string(containsString("Ground Beef")))
                .andExpect(content().string(containsString("Carnitas")))
                .andExpect(content().string(containsString("Choose your cheese:")))
                .andExpect(content().string(containsString("Monterrey Jack")))
                .andExpect(content().string(containsString("Determine your veggies:")))
                .andExpect(content().string(containsString("Lettuce")))
                .andExpect(content().string(containsString("Name your taco creation:")))
                .andExpect(content().string(containsString("Submit your taco")))
    }

    def "should process post request an redirect to orderForm"() {
        expect:
        mockMvc.perform(post("/design"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/current"))
    }
}
