package tacos.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import spock.lang.Specification
import tacos.Ingredient
import tacos.security.SecurityConfig

@SpringBootTest
@DataJpaTest
@Import(SecurityConfig)
class IngredientRepositoryTest extends Specification {

    @Autowired
    IngredientRepository ingredientRepository

    def "should save and return new Ingredient"() {
        given:
        def newIngredient = new Ingredient("NEW", "NEW INGREDIENT", Ingredient.Type.WRAP)

        when:
        def savedIngredient = ingredientRepository.save(newIngredient)

        then:
        newIngredient == savedIngredient
    }

    def "should find new saved ingredient"() {
        given:
        def newIngredient = new Ingredient("NEW", "NEW INGREDIENT", Ingredient.Type.WRAP)
        ingredientRepository.save(newIngredient)

        when:
        def result = ingredientRepository.findById("NEW")

        then:
        result.get() == newIngredient
    }

    def "should find all saved ingredients"() {
        given:
        ingredientRepository.deleteAll()

        1.upto(5) {
            def ingredientName = "INGREDIENT_${it}"
            ingredientRepository.save(new Ingredient(ingredientName, "NEW INGREDIENT # ${it}", Ingredient.Type.WRAP))
        }

        when:
        def resultList = ingredientRepository.findAll()

        then:
        resultList.size() == 5
        resultList.collect { ingredient -> ingredient.id } == ["INGREDIENT_1", "INGREDIENT_2", "INGREDIENT_3", "INGREDIENT_4", "INGREDIENT_5"]
    }

}
