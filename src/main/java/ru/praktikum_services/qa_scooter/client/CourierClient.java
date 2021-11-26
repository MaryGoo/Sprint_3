package ru.praktikum_services.qa_scooter.client;

import io.qameta.allure.Step;
import ru.praktikum_services.qa_scooter.models.Courier;
import ru.praktikum_services.qa_scooter.models.CourierCredentials;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static ru.praktikum_services.qa_scooter.apiData.EndPoints.COURIER_PATH;

public class CourierClient extends RestAssuredClient {

    @Step("Создание курьера")
    public boolean create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(courier)
                .log().body()
                .when()
                .post(COURIER_PATH)
                .then()
                .log().body()
                .assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .path("ok");
    }

    @Step("Попытка создание курьера без обязательных полей")
    public String createWithoutMandatoryField(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(courier)
                .log().body()
                .when()
                .post(COURIER_PATH)
                .then()
                .log().body()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");
    }

    @Step("Попытка создание курьера со повторяющимся логином")
    public String createWithSameLogin(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(courier)
                .log().body()
                .when()
                .post(COURIER_PATH)
                .then()
                .log().body()
                .assertThat()
                .statusCode(SC_CONFLICT)
                .extract()
                .path("message");
    }

    @Step("Логирование в систему под курьером")
    public int login(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .log().body()
                .when()
                .post(COURIER_PATH + "/login")
                .then()
                .log().body()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("id");
    }

    @Step("Попытка логирования в систему под курьером без обязательных полей")
    public String loginWithoutMandatoryFields(CourierCredentials courierCredentials){
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .log().body()
                .when()
                .post(COURIER_PATH + "/login")
                .then()
                .log().body()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");
    }

    @Step("Попытка логирования в систему под курьером с неверными данными")
    public String loginWithIncorrectValue(CourierCredentials courierCredentials){
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .log().body()
                .when()
                .post(COURIER_PATH + "/login")
                .then()
                .log().body()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .extract()
                .path("message");
    }

    @Step("Удаление курьера")
    public boolean delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + "/" + courierId)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("ok");
    }
}