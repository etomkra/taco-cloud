package tacos.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import spock.lang.Specification
import spock.lang.Unroll

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
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>()

        mockMvc.perform(post("/orders")
                .param("name", "Tom")
                .param("street", "Polczeki")
                .param("city", "Warsaw")
                .param("state", "Mazowieckie")
                .param("zip", "03-984")
                .param("ccNumber", "4532023303729686")
                .param("ccExpiration", "12/39")
                .param("ccCVV", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
    }

    @Unroll
    def "should process post request, stay on orderForm page and report missing #fields"() {
        given:
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>()

        expect:
        mockMvc.perform(post("/orders")
                .params(inputForm))
                .andExpect(model().attributeDoesNotExist(inputForm.findAll { !it.value }.collect {
            it.key
        } as String[]))
                .andExpect(status().is2xxSuccessful())

        where:
        fields                                         | inputForm
        "name"                                         | [name: [""], street: ["Grochowska"], city: ["Warsaw"], state: ["Mazowieckie"], zip: ["03-984"], ccNumber: ["4532023303729686"], ccExpiration: ["12/35"], ccCVV: ["123"]] as LinkedMultiValueMap
        "street,state,zip,ccNumber,ccExpiration,ccCVV" | [name: ["Tom"], street: [""], city: [""], state: [""], zip: [""], ccNumber: [""], ccExpiration: [""], ccCVV: [""]] as LinkedMultiValueMap
    }

    @Unroll
    def "should process post request stay on orderForm page and report errors for #testDesc"() {
        given:
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>()

        expect:
        mockMvc.perform(post("/orders")
                .params(inputForm))
                .andExpect(model().attributeHasFieldErrorCode("order", fieldName, error))
                .andExpect(status().is2xxSuccessful())

        where:
        testDesc                       | inputForm                                                                                                                                                                                            || fieldName      | error
        "incorrect Credit Card number" | [name: ["Tom"], street: ["Grochowska"], city: ["Warsaw"], state: ["Mazowieckie"], zip: ["03-984"], ccNumber: ["1234"], ccExpiration: ["12/35"], ccCVV: ["123"]] as LinkedMultiValueMap               || "ccNumber"     | "CreditCardNumber"
        "incorrect Expiration Date"    | [name: ["Tom"], street: ["Grochowska"], city: ["Warsaw"], state: ["Mazowieckie"], zip: ["03-984"], ccNumber: ["4532023303729686"], ccExpiration: ["12/2035"], ccCVV: ["123"]] as LinkedMultiValueMap || "ccExpiration" | "Pattern"
        "incorrect Credit Card CVV"    | [name: ["Tom"], street: ["Grochowska"], city: ["Warsaw"], state: ["Mazowieckie"], zip: ["03-984"], ccNumber: ["4532023303729686"], ccExpiration: ["12/35"], ccCVV: ["12345"]] as LinkedMultiValueMap || "ccCVV"        | "Digits"
    }

}
