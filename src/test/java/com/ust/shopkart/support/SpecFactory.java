package com.ust.shopkart.support;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

public class SpecFactory {

    public static final String BASE_URL = TestEnvironment.required("BACKEND_URL");

    public static Map<String,String> generateTokenBody =
            Map.of(TestEnvironment.required("GRANT_TYPE"),TestEnvironment.required("GRANT_SECRET"));



    public static RequestSpecification authLoginRequest =new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .setBasePath("/api")
            .addHeader("Content-Type","application/json")
            .build();




    public static RequestSpecification commonJsonRequest =
            new RequestSpecBuilder()
                    .setBaseUri(BASE_URL)
                    .setBasePath("/api")
                    .addHeader("Content-Type","application/json")
                    .build();

}
