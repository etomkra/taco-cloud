package tacos

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import spock.lang.Specification
import spock.mock.DetachedMockFactory
import tacos.data.IngredientRepository


class WebMvcTestSpecification extends Specification {

    @TestConfiguration
    static class MockConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        IngredientRepository ingredientRepository() {
            def mockedRepo = detachedMockFactory.Mock(IngredientRepository)
        }

    }
}