package ru.praktikum_services.qa_scooter.CourierDelete;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.praktikum_services.qa_scooter.client.CourierClient;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class DeleteCourierWithoutId {
    private CourierClient courierClient;
    private ValidatableResponse response;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
    }

    @Test
    @Story("Удаление курьера")
    @Description("Удаление курьера. Запрос без id")
    @DisplayName("Удаление курьера. Запрос без id")
    public void checkCourierCanBeDeleted(){
        //Act
        response = courierClient.delete();

        //Assert
        response.assertThat().statusCode(SC_BAD_REQUEST);
        //TODO уточнить наличие дефекта. по факту приходит: 404
        response.assertThat().extract().path("message").equals("Недостаточно данных для удаления курьера");
        //TODO уточнить наличие дефекта. по факту приходит: "Not Found."
    }
}