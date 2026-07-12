package com.ust.shopkart.tests;

import com.ust.shopkart.model.DummyOrderRow;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Shopkart Checkout")
@Feature("Dummy Order Persistence")
@Owner("SDET Trainer")
public class OrderTest extends AbstractShopkartTest {

    @Test
    @DisplayName("Create and fetch a dummy order")
    @Story("Order round-trips through the database correctly")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Creates a dummy order via the factory, then reads it back through the repository and verifies every field matches")
    void createAndFetchDummyOrder() {
        DummyOrderRow created = factory.createPlacedOrder("Chennai, Tamil Nadu");

        DummyOrderRow fetched = repository.findById(created.id());

        assertEquals(created.id(), fetched.id());
        assertEquals(created.cartId(), fetched.cartId());
        assertEquals("PLACED", fetched.status());
        assertEquals(created.totalPaise(), fetched.totalPaise());
        assertEquals("Chennai, Tamil Nadu", fetched.address());
    }
}