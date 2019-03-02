package tacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacos.data.jdbcTemplate.JdbcIngredientRepository;

import java.util.stream.StreamSupport;

@Component
public class IngredientConverter implements Converter<String, Ingredient> {

    private final JdbcIngredientRepository ingredientRepo;

    @Autowired
    public IngredientConverter(JdbcIngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String sourceName) {
        return StreamSupport.stream(ingredientRepo.findAll().spliterator(), false)
                .filter(ingredient -> ingredient.getId().equals(sourceName))
                .findFirst().orElse(null);
    }

}
