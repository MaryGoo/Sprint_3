package ru.praktikum_services.stellarburgers.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.praktikum_services.stellarburgers.model.User;

import static io.restassured.RestAssured.given;
import static ru.praktikum_services.stellarburgers.apidata.EndPoints.*;
import static ru.praktikum_services.stellarburgers.client.RestAssuredClient.getBaseSpec;

public class UserClient {

    @Step("Создание пользователя")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .log().body()
                .when()
                .post(AUTH_REGISTER)
                .then()
                .log().body();
    }

    @Step("Логирование в систему под пользователем")
    public ValidatableResponse login(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .log().body()
                .when()
                .post(AUTH_LOGIN)
                .then()
                .log().body();
    }

    @Step("Изменение пользователя")
    public ValidatableResponse updateUserInfo(String token, String body) {
        return given()
                .headers("Authorization", token)
                .spec(getBaseSpec())
                .body(body)
                .log().body()
                .when()
                .patch(AUTH_UPDATE)
                .then()
                .log().body();
    }

    @Step("Удалить пользователя")
    public void delete(String accessToken) {
        if (accessToken == null) {
            return;
        }
        given()
                .header("Authorization",accessToken)
                .spec(getBaseSpec())
                .when()
                .delete(AUTH_UPDATE)
                .then()
                .statusCode(202);
    }
}
