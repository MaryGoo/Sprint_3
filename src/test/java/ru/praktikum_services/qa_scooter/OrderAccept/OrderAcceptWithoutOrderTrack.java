package ru.praktikum_services.qa_scooter.OrderAccept;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.client.OrderClient;
import ru.praktikum_services.qa_scooter.models.Courier;
import ru.praktikum_services.qa_scooter.models.CourierCredentials;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class OrderAcceptWithoutOrderTrack {
    private OrderClient orderClient;
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;
    private ValidatableResponse responseAccept;


    @Before
    public void setUp(){
        orderClient = new OrderClient();
        courierClient =  new CourierClient();
        courier = Courier.getRandom();
        courierClient.create(courier);
    }

    @Test
    @Story("Принять заказ")
    @Description("Попытка принять заказ. В запросе не указан трек Id")
    @DisplayName("Попытка принять заказ. В запросе не указан трек Id")
    public void checkOrderCanNotBeAcceptedWithoutOrderTrack(){
        //Arrange
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        //Act
        responseAccept = orderClient.acceptOrder(courierId);

        //Assert
        responseAccept.assertThat().statusCode(SC_NOT_FOUND);
        //TODO статус ответа в доке : 400 Bad Request. по факту 404. уточнить какой верный
        assertThat(responseAccept.extract().path("message"), equalTo("Not Found."));
        //TODO в доке: "message":  "Недостаточно данных для поиска". по факту "Not Found." если через UI такую ситуацию не воспроизвести, завести дефект в бэклог
    }
}