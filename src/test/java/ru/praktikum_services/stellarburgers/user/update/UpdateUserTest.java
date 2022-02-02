package ru.praktikum_services.stellarburgers.user.update;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.stellarburgers.client.UserClient;
import ru.praktikum_services.stellarburgers.model.User;

import java.util.HashMap;
import java.util.Locale;

import static org.apache.http.HttpStatus.SC_OK;

public class UpdateUserTest {
    private UserClient userClient;
    private ValidatableResponse response;
    private User user;
    private String token;
    String newEmail;
    String newPassword;
    String newName;

    static Faker faker = new Faker(new Locale("en_EN"));

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandom();
        token = userClient.create(user).extract().path("accessToken");
    }

    @After
    public void tearDown() {
        userClient.delete(token);
    }

    @Test
    @Story("Изменение пользователя")
    @Description("Изменение пользователя. Изменение почты")
    @DisplayName("Изменение пользователя. Изменение почты")
    public void checkUserCanBeUpdateEmail() {

        newEmail = RandomStringUtils.randomAlphabetic(7) + "@mail.ru";
        response = userClient.updateUserInfo(token, "{\"email\": \"" + newEmail + "\"}");

        response.assertThat().statusCode(SC_OK);
        response.assertThat().extract().path("success").equals(true);
        HashMap<String, String> userInfo = response.extract().path("user");
        Assert.assertEquals(newEmail.toLowerCase(), userInfo.get("email"));
        Assert.assertEquals(user.name.toLowerCase(), userInfo.get("name"));
    }

    @Test
    @Story("Изменение пользователя")
    @Description("Изменение пользователя. Изменение пароля")
    @DisplayName("Изменение пользователя. Изменение пароля")
    public void checkUserCanBeUpdatePassword() {

        newPassword = RandomStringUtils.randomAlphabetic(10);
        response = userClient.updateUserInfo(token, "{\"password\": \"" + newPassword + "\"}");

        //Проверка успешной замены пароля
        response.assertThat().statusCode(SC_OK);
        response.assertThat().extract().path("success").equals(true);
        HashMap<String, String> userInfo = response.extract().path("user");
        Assert.assertEquals(user.name.toLowerCase(), userInfo.get("name"));
        Assert.assertEquals(user.email.toLowerCase(), userInfo.get("email"));
        //Проверка авторизации под пользователем с новым паролем
        User userNew = new User(user.email, newPassword, user.name);
        ValidatableResponse responseLoginNewPass = userClient.login(userNew);
        responseLoginNewPass.assertThat().statusCode(SC_OK);
        responseLoginNewPass.assertThat().extract().path("success").equals(true);
    }

    @Test
    @Story("Изменение пользователя")
    @Description("Изменение пользователя. Изменение имени")
    @DisplayName("Изменение пользователя. Изменение имени")
    public void checkUserCanBeUpdateName() {

        newName = faker.name().username();
        response = userClient.updateUserInfo(token, "{\"name\": \"" + newName + "\"}");

        response.assertThat().statusCode(SC_OK);
        response.assertThat().extract().path("success").equals(true);
        HashMap<String, String> userInfo = response.extract().path("user");
        Assert.assertEquals(user.email.toLowerCase(), userInfo.get("email"));
        Assert.assertEquals(newName.toLowerCase(), userInfo.get("name"));
    }
}