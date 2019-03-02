package tacos

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import spock.lang.Specification
import spock.mock.DetachedMockFactory
import tacos.data.jdbcTemplate.IngredientRepository
import tacos.data.jdbcTemplate.OrderRepository
import tacos.data.jdbcTemplate.TacoRepository


class MockedSpecification extends Specification {

    @TestConfiguration
    static class MockConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        IngredientRepository ingredientRepository() {
            detachedMockFactory.Mock(IngredientRepository)
        }

        @Bean
        TacoRepository tacoRepository() {
            detachedMockFactory.Mock(TacoRepository)
        }

        @Bean
        OrderRepository orderRepository() {
            detachedMockFactory.Mock(OrderRepository)
        }

        @Bean
        IngredientConverter ingredientConverter() {
            detachedMockFactory.Mock(IngredientConverter)
        }

    }
}