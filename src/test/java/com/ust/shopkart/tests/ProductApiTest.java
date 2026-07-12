package com.ust.shopkart.tests;

import com.ust.shopkart.api.client.ProductClient;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductApiTest {

    ProductClient products = new ProductClient();

    @Test
    @DisplayName("Search product by keyword dynamically ")
    void searchProduct() {

        Response response = products.searchProductByKeyword("SKU-CAP")
                .then()
                .statusCode(200)
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        int size = jsonPath.getList("$").size();

        assertEquals(200, response.statusCode());
        assertEquals("SKU-CAP", jsonPath.getString("[0].sku"));
        assertEquals("Everyday Cap",jsonPath.getString("[0].name"));
        assertEquals(0, jsonPath.getInt("[0].stock"));

        for (int i = 0; i < size; i++)
        {

            System.out.println("Product " + (i + 1));
            System.out.println("SKU        : " + jsonPath.getString("[" + i + "].sku"));
            System.out.println("Name       : " + jsonPath.getString("[" + i + "].name"));
            System.out.println("Description: " + jsonPath.getString("[" + i + "].description"));
            System.out.println("Category   : " + jsonPath.getString("[" + i + "].category"));
            System.out.println("Price      : " + jsonPath.getInt("[" + i + "].pricePaise"));
            System.out.println("Stock      : " + jsonPath.getInt("[" + i + "].stock"));
            System.out.println("Image Key  : " + jsonPath.getString("[" + i + "].imageKey"));
            System.out.println("--------------------------------");
        }
    }
}