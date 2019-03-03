package tacos.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import tacos.Ingredient
import tacos.Order
import tacos.Taco

@SpringBootTest
@DataJpaTest
class OrderRepositoryTest extends Specification {
    @Autowired
    IngredientRepository ingredientRepository

    @Autowired
    OrderRepository orderRepository

    @Autowired
    TacoRepository tacoRepository


    def "should Save Order in the repository"() {
        given:
        def ingredients = [
                ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP)),
                ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP))
        ]
        def taco = tacoRepository.save(new Taco(null, null, "TEST", ingredients))

        Order order = Order.builder()
                .name("Tom")
                .street("Polczeki")
                .city("Warsaw")
                .state("MZ")
                .zip("03-984")
                .ccNumber("4532023303729686")
                .ccExpiration("12/39")
                .ccCVV("123")
                .tacos([taco])
                .build()


        when:
        def savedOrder = orderRepository.save(order)

        then:
        savedOrder == order

    }
}
