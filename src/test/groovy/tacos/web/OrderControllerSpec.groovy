package tacos.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(OrderController.class)
class OrderControllerSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    def "should process get request and render orderForm view"() {
        expect:
        mockMvc.perform(get("/orders/current"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderForm"))
                .andExpect(content().string(containsString("Order your taco creations!")))
                .andExpect(content().string(containsString("Submit order")))
    }

    def "should process post request an redirect to home"() {
        expect:
        mockMvc.perform(post("/orders"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
    }

}
