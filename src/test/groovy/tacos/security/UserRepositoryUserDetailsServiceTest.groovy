package tacos.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import spock.lang.Specification
import tacos.User
import tacos.data.UserRepository

@SpringBootTest
class UserRepositoryUserDetailsServiceTest extends Specification {

    @Autowired
    UserRepository userRepository

    @Autowired
    UserDetailsService userDetailsService

    def "should load userData for testUser"() {
        given:
        def newUser = userRepository.save(new User("testUser", "pass123", "Name Surname", "Dolna 11", "Warsaw", "MZ", "02-345", "221234567"))

        when:
        def loadedUser = userDetailsService.loadUserByUsername("testUser")

        then:
        loadedUser == newUser
    }

    def "should throw an exception for not existing user"() {
        when:
        userDetailsService.loadUserByUsername("notExistingUser")

        then:
        def exception = thrown(UsernameNotFoundException)
        exception.getClass() == UsernameNotFoundException
        exception.getMessage() == "Username notExistingUser not found in the UserRepository"

    }
}
