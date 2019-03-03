package tacos

import spock.lang.Specification
import spock.lang.Unroll
import tacos.data.jdbcTemplate.JdbcIngredientRepository

class IngredientConverterTest extends Specification {

    @Unroll
    def "should #testDesc"() {
        given:
        def ingredientRepository = Mock(JdbcIngredientRepository)
        ingredientRepository.findAll() >> [new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP)]

        def converter = new IngredientConverter(ingredientRepository)

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
