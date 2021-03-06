package tacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.data.IngredientRepository;
import tacos.data.UserRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class TacoCloudApplication {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);

    }

    @PostConstruct
    public void insertSampleDataToDb() {
        userRepository.save(new User("admin", passwordEncoder.encode("admin1"), "Admin Adminski", "Grochowska 123", "Warsaw", "MZ", "03-984", "221234567"));
        ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
        ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
        ingredientRepository.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
        ingredientRepository.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
        ingredientRepository.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
        ingredientRepository.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
        ingredientRepository.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
        ingredientRepository.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
        ingredientRepository.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
        ingredientRepository.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
    }
}

