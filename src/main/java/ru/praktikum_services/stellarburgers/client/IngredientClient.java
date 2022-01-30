package ru.praktikum_services.stellarburgers.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static ru.praktikum_services.stellarburgers.apidata.EndPoints.INGREDIENTS;
import static ru.praktikum_services.stellarburgers.client.RestAssuredClient.getBaseSpec;

public class IngredientClient {

    @Step("Получение списка ингридиентов")
    public ValidatableResponse getList() {
        return given()
                .spec(getBaseSpec())
                .log().uri()
                .when()
                .get(INGREDIENTS)
                .then();
    }
}