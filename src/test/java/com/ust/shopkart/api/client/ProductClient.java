package com.ust.shopkart.api.client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static com.ust.shopkart.support.SpecFactory.*;

public class ProductClient {

    public Response searchProductByKeyword(String productName) {

        return given()
                .spec(commonJsonRequest)
                .queryParam("q", productName)
                .get("/products");
    }

    public Response getBySku(String sku) {

        return given()
                .spec(commonJsonRequest)
                .get("/products/" + sku);
    }

}