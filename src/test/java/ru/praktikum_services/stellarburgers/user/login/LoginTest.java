package ru.praktikum_services.stellarburgers.user.login;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.praktikum_services.stellarburgers.client.UserClient;
import ru.praktikum_services.stellarburgers.model.User;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class LoginTest {
    private UserClient userClient;
    private User user;
    private ValidatableResponse response;
    private String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
        user =  User.getRandom();
        userClient.create(user);
    }

    @After
    public void tearDown() {
        userClient.delete(accessToken);
    }

    @Test
    @Story("Логин пользователя в системе")
    @Description("Логин пользователя в системе. Позитивный сценарий")
    @DisplayName("Логин пользователя в системе. Позитивный сценарий")
    public void checkNewCourierCanLogin() {
        //Act
        response = userClient.login(user);

        //Assert
        response.assertThat().statusCode(SC_OK);
        response.assertThat().extract().path("success").equals(true);
        response.assertThat().extract().path("accessToken").equals(is(not((null))));
        response.assertThat().extract().path("refreshToken").equals(is(not((null))));
        response.assertThat().extract().path("user").equals(is(not((null))));
        accessToken = response.extract().path("accessToken");
    }
}