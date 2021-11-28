package ru.praktikum_services.qa_scooter.OrdersGetList;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.praktikum_services.qa_scooter.client.OrderClient;
import ru.praktikum_services.qa_scooter.models.OrderFromDB;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static ru.praktikum_services.qa_scooter.utilities.Orders.generateOrderFromDBList;

public class OrderGet {
    private OrderClient orderClient;
    private ValidatableResponse response;
    private List<OrderFromDB> orderFromDBList;
    private List<Object> orderFromDBListObj;
    private int limit;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @Story("Получение списка заказов.")
    @Description("Получение списка заказов. Позитивный сценарий")
    @DisplayName("Получение списка заказов. Позитивный сценарий")
    public void checkListOfOrdersCanBeGet() {
        //Act
        limit = 10;
        response = orderClient.getList(limit);
        orderFromDBListObj = response.extract().jsonPath().getList("orders");
        orderFromDBList = generateOrderFromDBList(orderFromDBListObj);

        //Assert
        response.assertThat().statusCode(SC_OK);
        response.assertThat().body("data.orders", not(emptyArray()));
        assertThat(orderFromDBList.size(),equalTo(limit));
        orderFromDBList.forEach(orderFromDB -> assertThat(orderFromDB.id, is(not(0))));
    }
}
