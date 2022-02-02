package ru.praktikum_services.stellarburgers.user.update;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.stellarburgers.client.UserClient;
import ru.praktikum_services.stellarburgers.model.User;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class NegativeUpdateUserTest {
    private UserClient userClient;
    private ValidatableResponse response;
    private User user;
    private String token;
    String newEmail;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandom();
        userClient.create(user);
    }

    @After
    public void tearDown() {
        userClient.delete(token);
    }

    @Test
    @Story("Изменение пользователя")
    @Description("Изменение пользователя. Негативный сценарий: нет авторизации")
    @DisplayName("Изменение пользователя. Негативный сценарий: нет авторизации")
    public void checkUserCanNotBeCreatedWithoutAuthorization() {
        token = RandomStringUtils.randomAlphabetic(20);

        newEmail = RandomStringUtils.randomAlphabetic(7) + "@mail.ru";
        response = userClient.updateUserInfo(token, "{\"email\": \"" + newEmail + "\"}");

        response.assertThat().statusCode(SC_UNAUTHORIZED);
        response.assertThat().extract().path("success").equals(false);
        response.assertThat().extract().path("message").equals("You should be authorised");
    }

    @Test
    @Story("Изменение пользователя")
    @Description("Изменение пользователя. Негативный сценарий: пользователь с таким email уже существует")
    @DisplayName("Изменение пользователя. Негативный сценарий: пользователь с таким email уже существует")
    public void checkUserCanNotBeUpdate() {

        User userClientTwo = User.getRandom();
        userClient.create(userClientTwo);
        newEmail = userClientTwo.email;
        token = userClient.login(user).extract().path("accessToken");

        response = userClient.updateUserInfo(token, "{\"email\": \"" + newEmail + "\"}");

        response.assertThat().statusCode(SC_FORBIDDEN);
        response.assertThat().extract().path("success").equals(false);
        response.assertThat().extract().path("message").equals("User with such email already exists");

    }
}