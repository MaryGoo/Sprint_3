package ru.praktikum_services.qa_scooter.CourierCreate;

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
import static org.hamcrest.Matchers.*;

public class CreateSameCourierTest {
    private CourierClient courierClient;
    private int courierId;
    Courier courier_1;
    Courier courier_2;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courier_1 = Courier.getRandom();
        courierClient.create(courier_1);
    }

    @After
    public void tearDown(){
        courierClient.delete(courierId);
    }

    @Test
    @Story("Создание курьера")
    @Description("Создание курьера с логином, который уже используется")
    @DisplayName("Создание курьера {courier} с логином, который уже используется")
    public void checkSameCourierCanNotBeCreatedSecondTime() {
        //Arrange
        courier_2 = Courier.getRandom();
        courier_2.setLogin(courier_1.getLogin());

        //Act
        String messageAboutCourierCreated = courierClient.createWithSameLogin(courier_2);
        courierId = courierClient.login(CourierCredentials.from(courier_1));

        //Assert
        assertThat(messageAboutCourierCreated, equalTo("Этот логин уже используется. Попробуйте другой."));
        //TODO уточнить у аналитика текст сообщения. в доке указано: "Этот логин уже используется", по факту приходит другой
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }
}