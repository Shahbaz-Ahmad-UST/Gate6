package com.ust.shopkart.repository;

import com.ust.shopkart.config.DatabaseConfig;
import com.ust.shopkart.model.DummyOrderRow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrderRepository {

    private final DatabaseConfig config;

    public OrderRepository(DatabaseConfig config) {
        this.config = config;
    }

    private Connection connect() throws Exception {
        return DriverManager.getConnection(
                config.jdbcUrl(),
                config.username(),
                config.password()
        );
    }

    public void reset() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM dummy_orders");
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset dummy_orders table", e);
        }
    }

    public long insert(long cartId, String status, int totalPaise, String address) {
        String sql = "INSERT INTO dummy_orders (cart_id, status, total_paise, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, cartId);
            ps.setString(2, status);
            ps.setInt(3, totalPaise);
            ps.setString(4, address);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
                throw new RuntimeException("Insert did not return a generated id");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to insert dummy order", e);
        }
    }

    public DummyOrderRow findById(long id) {
        String sql = "SELECT id, cart_id, status, total_paise, address FROM dummy_orders WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new RuntimeException("No dummy order found with id " + id);
                }
                return new DummyOrderRow(
                        rs.getLong("id"),
                        rs.getLong("cart_id"),
                        rs.getString("status"),
                        rs.getInt("total_paise"),
                        rs.getString("address")
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch dummy order " + id, e);
        }
    }
}