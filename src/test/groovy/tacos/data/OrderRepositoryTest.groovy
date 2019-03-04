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

    def "should find all Orders placed in Warsaw"() {
        given:
        def ingredients = [
                ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP)),
                ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP))
        ]
        def taco = tacoRepository.save(new Taco(null, null, "Test Taco", ingredients))

        Order orderFromWarsaw = Order.builder()
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

        Order orderFromWarsaw2 = Order.builder()
                .name("Tom")
                .street("Grochowska")
                .city("Warsaw")
                .state("MZ")
                .zip("03-995")
                .ccNumber("4532023303729686")
                .ccExpiration("12/39")
                .ccCVV("123")
                .tacos([taco])
                .build()

        Order orderFromKrakow = Order.builder()
                .name("Jerry")
                .street("Rynek")
                .city("Krakow")
                .state("MP")
                .zip("12-234")
                .ccNumber("4532023303729686")
                .ccExpiration("12/39")
                .ccCVV("123")
                .tacos([taco])
                .build()

        orderRepository.save(orderFromWarsaw)
        orderRepository.save(orderFromWarsaw2)
        orderRepository.save(orderFromKrakow)

        when:
        def foundOrders = orderRepository.findByCity("Warsaw")

        then:
        foundOrders.size() == 2
        foundOrders.collect { "${it.getCity()} ${it.getStreet()}" } == ["Warsaw Polczeki", "Warsaw Grochowska"]


    }
}