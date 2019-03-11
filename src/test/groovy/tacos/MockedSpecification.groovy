package tacos

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import spock.mock.DetachedMockFactory
import tacos.data.IngredientRepository
import tacos.data.OrderRepository
import tacos.data.TacoRepository
import tacos.data.UserRepository


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

        @Bean
        UserRepository userRepository() {
            detachedMockFactory.Mock(UserRepository)
        }

        @Bean
        PasswordEncoder passwordEncoder() {
            detachedMockFactory.Mock(PasswordEncoder)
        }

    }
}