package com.ust.shopkart.repository;

import com.ust.shopkart.config.DatabaseConfig;
import com.ust.shopkart.model.Order;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private final DatabaseConfig config;

    public OrderRepository(DatabaseConfig config) {
        this.config = config;
    }

    public Order save(Order order) {
        String sql = "INSERT INTO orders (sku, qty, price, order_date, shipped) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, order.sku());
            statement.setInt(2, order.qty());
            statement.setDouble(3, order.price());
            statement.setDate(4, Date.valueOf(order.orderDate()));
            statement.setBoolean(5, order.shipped());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    long id = keys.getLong(1);
                    return new Order(id, order.sku(), order.qty(), order.price(), order.orderDate(), order.shipped());
                }
            }
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM orders";

        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            result.next();
            return result.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> findAll() {
        String sql = "SELECT id, sku, qty, price, order_date, shipped FROM orders";
        List<Order> orders = new ArrayList<>();

        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                orders.add(new Order(
                        result.getLong("id"),
                        result.getString("sku"),
                        result.getInt("qty"),
                        result.getDouble("price"),
                        result.getDate("order_date").toLocalDate(),
                        result.getBoolean("shipped")
                ));
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void reset() {
        String sql = "TRUNCATE TABLE orders";

        try (Connection connection = openConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(config.jdbcUrl(), config.username(), config.password());
    }
}