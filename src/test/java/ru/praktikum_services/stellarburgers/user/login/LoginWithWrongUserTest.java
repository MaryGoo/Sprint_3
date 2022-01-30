package ru.praktikum_services.stellarburgers.user.login;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.praktikum_services.stellarburgers.client.UserClient;
import ru.praktikum_services.stellarburgers.model.User;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class LoginWithWrongUserTest {
    private UserClient userClient;
    private User user;
    private ValidatableResponse response;

    @Before
    public void setUp(){
        userClient = new UserClient();
        user =  User.getRandom();
    }

    @Test
    @Story("Логин пользователя в системе")
    @Description("Логин пользователя в системе. Позитивный сценарий")
    @DisplayName("Логин пользователя в системе. Позитивный сценарий")
    public void checkNewCourierCanLogin() {

        response = userClient.login(user);

        response.assertThat().statusCode(SC_UNAUTHORIZED);
        response.assertThat().extract().path("success").equals(false);
        response.assertThat().extract().path("message").equals("email or password are incorrect");
    }
}