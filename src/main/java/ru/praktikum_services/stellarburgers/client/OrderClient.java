package ru.praktikum_services.stellarburgers.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static ru.praktikum_services.stellarburgers.apidata.EndPoints.NEW_ORDER;

public class OrderClient extends RestAssuredClient {

    @Step("Создать закать для пользователя")
    public ValidatableResponse create(HashMap ingredients, String token) {
        return given()
                .headers("Authorization", token)
                .spec(getBaseSpec())
                .body(ingredients)
                .log().body()
                .when()
                .post(NEW_ORDER)
                .then()
                .log().body();
    }

    @Step("Получить заказ для авторизованного пользователя")
    public ValidatableResponse getOrders(String accessUserToken) {
        return given()
                .header("Authorization", accessUserToken)
                .spec(getBaseSpec())
                .when()
                .get(NEW_ORDER)
                .then()
                .log().body();
    }

    @Step("Получить заказ для не авторизованного пользователя")
    public ValidatableResponse getOrdersWithoutToken() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(NEW_ORDER)
                .then()
                .log().body();
    }
}