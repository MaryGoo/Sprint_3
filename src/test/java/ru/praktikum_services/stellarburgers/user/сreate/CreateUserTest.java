package ru.praktikum_services.stellarburgers.user.сreate;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.praktikum_services.stellarburgers.client.UserClient;
import ru.praktikum_services.stellarburgers.model.User;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class CreateUserTest {
    private UserClient userClient;
    private ValidatableResponse response;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @Story("Создание пользователя")
    @Description("Создание уникального пользователя")
    @DisplayName("Создание уникального пользователя")
    public void checkUserCanBeCreated() {
        User user = User.getRandom();

        response = userClient.create(user);

        response.assertThat().statusCode(SC_OK);
        response.assertThat().extract().path("success").equals(true);
        response.assertThat().extract().path("accessToken").equals(is(not((null))));
        response.assertThat().extract().path("refreshToken").equals(is(not((null))));
    }

    @Test
    @Story("Создание существующего пользователя")
    @Description("Создание существующего пользователя")
    @DisplayName("Создание существующего пользователя")
    public void checkTheSameUserCanNotBeCreated() {
        User user = User.getRandom();
        response = userClient.create(user);
        response.assertThat().statusCode(SC_OK);

        response = userClient.create(user);

        response.assertThat().extract().path("success").equals(false);
        response.assertThat().extract().path("message").equals("User already exists");
        response.assertThat().statusCode(SC_FORBIDDEN);
    }
}