package com.ust.shopkart.tests;

import com.ust.shopkart.api.client.ProductClient;
import com.ust.shopkart.report.ExtentTestListener;
import io.qameta.allure.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Shopkart API")
@Feature("Product Search")
@Owner("SHAHBAZ AHMAD")
@ExtendWith(ExtentTestListener.class)
public class ProductApiTest {

    private static final Logger log = LoggerFactory.getLogger(ProductApiTest.class);

    ProductClient products = new ProductClient();

    @Test
    @DisplayName("Search product by keyword dynamically ")
    @Story("Searching by SKU returns the matching product with correct fields")
    @Severity(SeverityLevel.NORMAL)
    @Description("Searches for SKU-CAP and verifies sku, name, and stock in the first result")
    void searchProduct() {

        Response response = products.searchProductByKeyword("SKU-CAP")
                .then()
                .statusCode(200)
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        int size = jsonPath.getList("$").size();

        assertEquals(200, response.statusCode());
        assertEquals("SKU-CAP", jsonPath.getString("[0].sku"));
        assertEquals("Everyday Cap", jsonPath.getString("[0].name"));
        assertEquals(0, jsonPath.getInt("[0].stock"));

        for (int i = 0; i < size; i++) {
            log.info("Product {}: sku={}, name={}, description={}, category={}, price={}, stock={}, imageKey={}",
                    i + 1,
                    jsonPath.getString("[" + i + "].sku"),
                    jsonPath.getString("[" + i + "].name"),
                    jsonPath.getString("[" + i + "].description"),
                    jsonPath.getString("[" + i + "].category"),
                    jsonPath.getInt("[" + i + "].pricePaise"),
                    jsonPath.getInt("[" + i + "].stock"),
                    jsonPath.getString("[" + i + "].imageKey"));
        }
    }
}