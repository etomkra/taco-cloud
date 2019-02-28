package tacos

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Unroll
import tacos.data.IngredientRepository

@SpringBootTest
class IngredientConverterTest extends MockedSpecification {
    @Autowired
    IngredientRepository ingredientRepository

    @Autowired
    IngredientConverter converter


    @Unroll
    def "should #testDesc"() {
        given:
        ingredientRepository.findAll() >> [new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP)]

        when:
        def result = converter.convert(convertFrom)

        then:
        result == convertTo

        where:
        testDesc                       | convertFrom    || convertTo
        "convert String to Ingredient" | "FLTO"         || new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP)
        "return Null"                  | "Not Existing" || null
    }
}
