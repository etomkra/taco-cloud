package tacos.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import spock.lang.Specification
import tacos.Ingredient
import tacos.Taco
import tacos.security.SecurityConfig

@SpringBootTest
@DataJpaTest
@Import(SecurityConfig)
class TacoRepositoryTest extends Specification {

    @Autowired
    TacoRepository tacoRepository

    @Autowired
    IngredientRepository ingredientRepository

    def "should save and find Taco and Ingredients"() {
        given:
        def ingredients = [
                ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP)),
                ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP))
        ]
        Taco taco = new Taco(null, null, "TEST_TACO", ingredients)

        when:
        tacoRepository.save(taco)

        then:
        def resultList = tacoRepository.findAll()
        resultList.size() == 1

        def foundTaco = resultList.getAt(0)
        verifyAll {
            foundTaco.getName() == "TEST_TACO"
            foundTaco.id != null
            foundTaco.createdAt != null
        }

        def savedIngredients = foundTaco.getIngredients()
        savedIngredients.size() == 2
        savedIngredients.collect { it.getId() } == ["FLTO", "COTO"]

    }
}
