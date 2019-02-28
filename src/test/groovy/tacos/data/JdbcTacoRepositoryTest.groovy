package tacos.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.jdbc.JdbcTestUtils
import spock.lang.Specification
import tacos.Ingredient
import tacos.Taco

@SpringBootTest
@AutoConfigureTestDatabase
class JdbcTacoRepositoryTest extends Specification {

    @Autowired
    JdbcTacoRepository tacoRepository

    @Autowired
    JdbcTemplate jdbc;


    def "should Save Taco in the repository"() {
        given:
        def ingredients = [
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP)
        ]
        Taco taco = new Taco(null, null, "TEST", ingredients)

        when:
        tacoRepository.save(taco)

        then: "New Taco is saved"
        JdbcTestUtils.countRowsInTableWhere(jdbc, "TACO", "NAME = 'TEST'") == 1

        and: "Ingredients mapping is saved"
        JdbcTestUtils.countRowsInTableWhere(jdbc,
                "TACO_INGREDIENTS",
                "TACO IN (SELECT ID FROM TACO WHERE NAME = 'TEST') AND INGREDIENT IN ('FLTO','COTO')") == 2

    }
}
