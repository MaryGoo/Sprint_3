package ru.praktikum_services.qa_scooter.CourierCreate;

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

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class CreateNewCourierWithAllFieldsTest {

    private CourierClient courierClient;
    private int courierId;
    private ValidatableResponse response;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
    }

    @After
    public void tearDown(){
        courierClient.delete(courierId);
    }

    @Test
    @Story("Создание курьера")
    @Description ("Создание уникального курьера со всеми полями")
    @DisplayName("Создание уникального курьера {courier} со всеми полями")
    public void checkCourierCanBeCreatedWithAllFields(){
        //Arrange
        Courier courier = Courier.getRandom();

        //Act
        response = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        //Assert
        response.assertThat().statusCode(SC_CREATED);
        response.assertThat().extract().path("ok").equals(true);
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }

    @Test
    @Story("Создание курьера")
    @Description ("Создание уникального курьера без обязательного поля FirstName")
    @DisplayName("Создание уникального курьера {courier} без обязательного поля FirstName")
    public void checkCourierCanBeCreatedWithoutFirstName(){
        //Arrange
        Courier courier = Courier.getRandom();
        courier.setFirstName(null);

        //Act
        response = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        //Assert
        response.assertThat().statusCode(SC_CREATED);
        response.assertThat().extract().path("ok").equals(true);
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }
}