package com.ust.shopkart.api.client;

import io.restassured.response.Response;

import java.util.Map;

import static com.ust.shopkart.support.SpecFactory.*;
import static io.restassured.RestAssured.given;

public class AuthClient {

    public Response login(String email,
                          String password) {

        return given()
                .spec(authLoginRequest)
                .body(Map.of(
                        "email", email,
                        "password", password
                ))
                .post("auth/login");
    }

}