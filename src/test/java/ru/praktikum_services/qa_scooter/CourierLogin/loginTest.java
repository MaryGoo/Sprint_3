package ru.praktikum_services.qa_scooter.CourierLogin;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.models.Courier;
import ru.praktikum_services.qa_scooter.models.CourierCredentials;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class loginTest {
    private CourierClient courierClient;
    private int courierId;
    private Courier courier;
    private ValidatableResponse response;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courier = Courier.getRandom();
        courierClient.create(courier);
    }

    @After
    public void tearDown(){
        courierClient.delete(courierId);
    }

    @Test
    @Story("Логин курьера в системе")
    @Description("Логин курьера в системе. Позитивный сценарий")
    @DisplayName("Логин курьера в системе. Позитивный сценарий")
    public void checkNewCourierCanLogin() {
        //Act
        response = courierClient.login(CourierCredentials.from(courier));
        courierId = response.extract().path("id");

        //Assert
        response.assertThat().statusCode(SC_OK);
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }
}