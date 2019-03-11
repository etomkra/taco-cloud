package tacos.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import tacos.Ingredient
import tacos.IngredientConverter
import tacos.MockedSpecification
import tacos.data.IngredientRepository

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(value = DesignTacoController.class, secure = false)
class DesignTacoControllerSpec extends MockedSpecification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    IngredientRepository ingredientRepository

    @Autowired
    IngredientConverter ingredientConverter

    def "should render design page"() {
        given:
        ingredientRepository.findAll() >> [
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
        ]
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
        given:
        def ingredients = [
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP)
        ]

        expect:
        mockMvc.perform(
                post("/design")
                        .param("name", "TestTaco")
                        .sessionAttr("ingredients", ingredients)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/current"))
    }
}

