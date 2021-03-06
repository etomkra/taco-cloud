package tacos.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification
import tacos.User
import tacos.security.SecurityConfig

@DataJpaTest
@Import(SecurityConfig)
class UserRepositoryTest extends Specification {

    @Autowired
    UserRepository userRepository

    def "should save and and find new user"() {
        given:
        def newUser = new User("testUser", "pass123", "Name Surname", "Dolna 11", "Warsaw", "MZ", "02-345", "221234567")

        when:
        userRepository.save(newUser)

        then:
        def foundUser = userRepository.findByUsername("testUser")
        foundUser.getUsername() == newUser.getUsername()
        foundUser.getFullname() == newUser.getFullname()
    }

}
