package tacos.data.jdbcTemplate;

import tacos.Order;

public interface OrderRepository {
    Order save(Order order);
}