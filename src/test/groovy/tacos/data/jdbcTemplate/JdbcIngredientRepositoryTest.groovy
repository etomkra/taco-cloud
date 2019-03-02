package tacos.data.jdbcTemplate

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import tacos.Ingredient
import tacos.data.jdbcTemplate.IngredientRepository

@SpringBootTest
@AutoConfigureTestDatabase
class JdbcIngredientRepositoryTest extends Specification {

    @Autowired
    IngredientRepository ingredientRepository

    def "should find all pre-inserted ingredients"() {
        when:
        def result = ingredientRepository.findAll()

        then:
        result.collect { it -> it.id }.sort() == ['CARN', 'CHED', 'COTO', 'FLTO', 'GRBF', 'JACK', 'LETC', 'SLSA', 'SRCR', 'TMTO']
    }

    def "should save and found new ingredient"() {
        given:
        def newIngredient = new Ingredient("NEW", "NEW INGREDIENT", Ingredient.Type.WRAP)

        when:
        ingredientRepository.save(newIngredient)

        and:
        def foundIngredient = ingredientRepository.findOne("NEW")

        then:
        foundIngredient == newIngredient
    }
}
