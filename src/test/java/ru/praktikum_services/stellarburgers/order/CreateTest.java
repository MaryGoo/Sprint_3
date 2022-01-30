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

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class CreateTest {

    private OrderClient orderClient;
    private UserClient userClient;
    private IngredientClient ingredientClient;

    private HashMap<String, List> ingredientsHash = new HashMap<>();
    private ValidatableResponse response;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        userClient = new UserClient();
        ingredientClient = new IngredientClient();
        user = User.getRandom();
        userClient.create(user);


        List<String> ingredients = new ArrayList<>();
        List<String> clearIngredients = ingredientClient.getList().extract().path("data._id");
        for (int i = 0; i <= 3; i++) {
            ingredients.add(clearIngredients.get(i));
        }
        ingredientsHash.put("ingredients", ingredients);
    }

    @After
    public void tearDown() {
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Создать заказ без ингредиентов. Получить ошибку")
    public void createOrderWithoutIngredientsFail() {
        accessToken = userClient.login(user).extract().path("accessToken");

        response = orderClient.create(new HashMap<>(), accessToken);

        response.assertThat().statusCode(SC_BAD_REQUEST);
        response.assertThat().extract().path("success").equals(false);
        response.assertThat().extract().path("message").equals("Ingredient ids must be provided");
    }

    @Test
    @DisplayName("Создать заказ с не существующими ингредиентами. Получить ошибку")
    public void createOrderWithIncorrectIngredientFail() {
        accessToken = userClient.login(user).extract().path("accessToken");
        HashMap<String, List> ingredientsHash = new HashMap<>();
        ingredientsHash.put("ingredients", List.of("dkljhrfg","kdfjgh"));
        response = orderClient.create(ingredientsHash, accessToken);

        response.assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
        response.assertThat().extract().path("success").equals(false);
        response.assertThat().extract().path("message").equals("Ingredient ids must be provided");
    }

    @Test
    @DisplayName("Создать заказ без авторизации. Получить ошибку")
    public void createOrderWithoutAuthFail() {
        response = orderClient.create(ingredientsHash, "lkruhgilhgrluhsw");

        response.assertThat().statusCode(SC_BAD_REQUEST);
        response.assertThat().extract().path("success").equals(false);
        response.assertThat().extract().path("message").equals("Ingredient ids must be provided");
    }

    @Test
    @DisplayName("Создать заказ с существующими ингредиентами и авторизацией")
    public void createOrderWithAuthSuccess(){
        accessToken = userClient.login(user).extract().path("accessToken");
        response = orderClient.create(ingredientsHash, accessToken);

        response.assertThat().statusCode(SC_OK);
        response.assertThat().extract().path("success").equals(true);
        response.assertThat().extract().path("name").equals(is(not((null))));
        response.assertThat().extract().path("order.number").equals(is(not((null))));
    }
}