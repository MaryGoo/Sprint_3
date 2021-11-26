package ru.praktikum_services.qa_scooter.CourierLogin;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.models.Courier;
import ru.praktikum_services.qa_scooter.models.CourierCredentials;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class loginTest {
    private CourierClient courierClient;
    private int courierId;
    Courier courier;

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
    @Story("Создание курьера")
    @Description("Создание курьера с логином, который уже используется")
    @DisplayName("Создание курьера {courier} с логином, который уже используется")
    public void checkNewCourierCanLogin() {
        //Act
        courierId = courierClient.login(CourierCredentials.from(courier));

        //Assert
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }
}