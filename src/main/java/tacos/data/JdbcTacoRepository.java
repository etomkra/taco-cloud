package tacos.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;
import tacos.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        taco.getIngredients()
                .forEach(ingredient -> saveIngredientToTaco(ingredient, tacoId));
        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory("INSERT INTO TACO (NAME, CREATEDAT) VALUES (?, ?)", Types.VARCHAR, Types.TIMESTAMP);
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
        PreparedStatementCreator preparedStatementCreator = preparedStatementCreatorFactory
                .newPreparedStatementCreator(Arrays.asList(
                        taco.getName(),
                        new Timestamp(taco.getCreatedAt().getTime())
                ));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(preparedStatementCreator, keyHolder);
        Number key = keyHolder.getKey();

        long resultKey = key.longValue();
        return resultKey;
    }

    private void saveIngredientToTaco(
            Ingredient ingredient, long tacoId) {
        jdbc.update("INSERT INTO TACO_INGREDIENTS (TACO, INGREDIENT) VALUES (?, ?)", tacoId, ingredient.getId());
    }
}
