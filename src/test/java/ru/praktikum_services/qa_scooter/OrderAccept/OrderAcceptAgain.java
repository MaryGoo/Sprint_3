package ru.praktikum_services.qa_scooter.OrderAccept;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.client.OrderClient;
import ru.praktikum_services.qa_scooter.models.Courier;
import ru.praktikum_services.qa_scooter.models.CourierCredentials;
import ru.praktikum_services.qa_scooter.models.Order;

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class OrderAcceptAgain {
    private OrderClient orderClient;
    private CourierClient courierClient;
    private Order order;
    private Courier courier;
    private int orderId;
    private int courierId;
    private int orderTrack;
    private ValidatableResponse responseOrderAcceptGetFirstTime;
    private ValidatableResponse responseAccept;


    @Before
    public void setUp(){
        orderClient = new OrderClient();
        courierClient =  new CourierClient();
        order = Order.getRandom();
        courier = Courier.getRandom();
        courierClient.create(courier);
        orderTrack = orderClient.create(order).extract().path("track");
        orderId = orderClient.getOne(orderTrack).extract().path("order.id");
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
        orderClient.acceptOrder(orderId, courierId);
    }

    @After
    public void tearDown(){
        orderClient.finish(orderId);
    }

    @Test
    @Story("Принять заказ")
    @Description("Попытка принять заказ повторно")
    @DisplayName("Попытка принять заказ повторно")
    public void checkOrderCanBeAcceptedByCourier(){
        //Arrange
        responseOrderAcceptGetFirstTime = orderClient.getOne(orderTrack);

        //Act
        responseAccept = orderClient.acceptOrder(orderId, courierId);

        //Assert
        assertThat(responseOrderAcceptGetFirstTime.extract().path("order.status"), equalTo(1));
        responseAccept.assertThat().statusCode(SC_CONFLICT);
        assertThat(responseAccept.extract().path("message"), equalTo("Этот заказ уже в работе"));
    }
}