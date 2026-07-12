package com.ust.shopkart.support;

import com.ust.shopkart.config.DatabaseConfig;
import com.ust.shopkart.model.OrderRow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DbSupport {

    private final DatabaseConfig config;

    public DbSupport(DatabaseConfig config) {
        this.config = config;
    }

    public boolean isReachable() throws SQLException {
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT 1");
             ResultSet result = statement.executeQuery()) {
            return result.next() && result.getInt(1) == 1;
        }
    }

    public long countOrders() throws SQLException {
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM orders");
             ResultSet result = statement.executeQuery()) {
            result.next();
            return result.getLong(1);
        }
    }

    public boolean orderExists(long orderId) {

        try (Connection connection = openConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             "SELECT COUNT(*) FROM orders WHERE id = ?")) {

            statement.setLong(1, orderId);

            try (ResultSet result = statement.executeQuery()) {

                result.next();

                return result.getInt(1) == 1;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(config.jdbcUrl(), config.username(), config.password());
    }

    public OrderRow findOrder(long id) {

        try (Connection connection = openConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             "SELECT * FROM orders WHERE id = ?")) {

            statement.setLong(1, id);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    return new OrderRow(

                            result.getLong("id"),
                            result.getLong("customer_id"),
                            result.getLong("cart_id"),
                            result.getString("status"),
                            result.getInt("total_paise"),
                            result.getString("address"),
                            result.getTimestamp("created_at").toInstant(),
                            result.getTimestamp("updated_at").toInstant()

                    );
                }

                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to find order", e);
        }
    }

    public void deleteOrder(long orderId) {
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM orders WHERE id = ?")) {

            statement.setLong(1, orderId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Order not found with id: " + orderId);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete order with id: " + orderId, e);
        }
    }

    public int getCartItemQuantity(long cartId, String sku) {

        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT qty FROM cart_items WHERE cart_id = ? AND sku = ?")) {

            statement.setLong(1, cartId);
            statement.setString(2, sku);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return result.getInt("qty");
                }

                throw new RuntimeException("Item not found");

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getUnitPrice(long cartId, String sku) {

        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT unit_price_paise FROM cart_items WHERE cart_id = ? AND sku = ?")) {

            statement.setLong(1, cartId);
            statement.setString(2, sku);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return result.getInt("unit_price_paise");
                }

                throw new RuntimeException("Item not found");

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}