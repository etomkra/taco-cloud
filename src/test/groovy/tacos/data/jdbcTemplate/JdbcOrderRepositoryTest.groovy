package tacos.data.jdbcTemplate

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.jdbc.JdbcTestUtils
import spock.lang.Specification
import tacos.Ingredient
import tacos.Order
import tacos.Taco
import tacos.data.jdbcTemplate.OrderRepository
import tacos.data.jdbcTemplate.TacoRepository

@SpringBootTest
class JdbcOrderRepositoryTest extends Specification {
    @Autowired
    OrderRepository orderRepository

    @Autowired
    TacoRepository tacoRepository

    @Autowired
    JdbcTemplate jdbc

    def "should Save Order in the repository"() {
        given:
        def ingredients = [
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP)
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
        orderRepository.save(order)

        then: "New Order is saved"
        JdbcTestUtils.countRowsInTableWhere(
                jdbc, "TACO_ORDER",
                "deliveryName ='Tom' AND " +
                        "deliveryStreet = 'Polczeki' AND " +
                        "deliveryCity = 'Warsaw' AND " +
                        "deliveryState = 'MZ' AND " +
                        "deliveryZip = '03-984' AND " +
                        "ccNumber = '4532023303729686' AND " +
                        "ccExpiration = '12/39' AND " +
                        "ccCVV = '123'"
        ) == 1

        and: "New Taco_Order_Tacos mapping saved"
        JdbcTestUtils.countRowsInTableWhere(
                jdbc, "Taco_Order_Tacos",
                "TACOORDER IN (SELECT ID FROM TACO_ORDER WHERE " +
                        "deliveryName ='Tom' AND " +
                        "deliveryStreet = 'Polczeki' AND " +
                        "deliveryCity = 'Warsaw' AND " +
                        "deliveryState = 'MZ' AND " +
                        "deliveryZip = '03-984' AND " +
                        "ccNumber = '4532023303729686' AND " +
                        "ccExpiration = '12/39' AND " +
                        "ccCVV = '123')" +
                        "AND TACO IN " +
                        "(SELECT ID FROM TACO WHERE NAME = 'TEST')"
        ) == 1
    }
}
