package ru.praktikum_services.stellarburgers.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.stellarburgers.client.IngredientClient;
import ru.praktikum_services.stellarburgers.client.OrderClient;
import ru.praktikum_services.stellarburgers.client.UserClient;
import ru.praktikum_services.stellarburgers.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class GetOrdersFromIndividualUserTest {

    private UserClient userClient;
    public OrderClient orderClient;
    public IngredientClient ingredientClient;

    public User user = User.getRandom();
    public String accessUserToken;
    private ValidatableResponse response;
    private HashMap<String, List> orderHash;
    private List<String> ingredients = new ArrayList<>();


    @Before
    public void setUp() {
        userClient = new UserClient();
        ingredientClient = new IngredientClient();
        orderClient = new OrderClient();
        orderHash = new HashMap<>();
        accessUserToken = userClient.create(user).extract().path("accessToken");
        userClient.login(user);
        List<String> clearIngredients = ingredientClient.getList().extract().path("data._id");
        ingredients.add(clearIngredients.get(0));
        ingredients.add(clearIngredients.get(clearIngredients.size() - 1));
        orderHash.put("ingredients", ingredients);
    }


    @After
    public void tearDown() {
        userClient.delete(accessUserToken);
    }

    @Test
    @DisplayName("Проверка получения списка 1 заказа для зарегистрированных пользователей с 1 заказом")
    public void getOrdersFromLoginedUserWith1Orders() {
        orderClient.create(orderHash, accessUserToken);

        response = orderClient.getOrders(accessUserToken);

        response.assertThat().statusCode(SC_OK);
        response.assertThat().extract().path("total").equals(1);
        response.assertThat().extract().path("totalToday").equals(1);
    }

    @Test
    @DisplayName("Проверка получения списка из 50 заказов для зарегистрированных пользователей с 50-ью заказами")
    public void getOrdersFromLoginedUserWith50Orders() {
        for (int i = 0; i < 50; i++) {
            orderClient.create(orderHash, accessUserToken);
        }

        response = orderClient.getOrders(accessUserToken);

        response.assertThat().statusCode(SC_OK);
        response.assertThat().extract().path("total").equals(50);
        response.assertThat().extract().path("totalToday").equals(50);
    }


    @Test
    @DisplayName("Проверка получения пустого списка заказов для зарегистрированных пользователей без заказов")
    public void getOrdersFromLoginedUserWithoutOrders() {

        response = orderClient.getOrders(accessUserToken);

        response.assertThat().statusCode(SC_OK);
        response.assertThat().extract().path("total").equals(0);
        response.assertThat().extract().path("totalToday").equals(0);
    }

    @Test
    @DisplayName("Проверка ошибки получения списка заказов для не авторизованного пользователя")
    public void getOrdersFromNotLoginedUserWithOrders() {
        orderClient.create(orderHash, accessUserToken);

        response = orderClient.getOrdersWithoutToken();

        response.assertThat().statusCode(SC_UNAUTHORIZED);
        response.assertThat().extract().path("success").equals(false);
        response.assertThat().extract().path("message").equals("You should be authorised");
    }
}