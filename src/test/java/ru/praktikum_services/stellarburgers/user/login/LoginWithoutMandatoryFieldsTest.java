package ru.praktikum_services.stellarburgers.user.login;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.stellarburgers.client.UserClient;
import ru.praktikum_services.stellarburgers.model.User;

import java.util.Arrays;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@RunWith(value = Parameterized.class)
public class LoginWithoutMandatoryFieldsTest {
    private UserClient userClient;
    private User body;
    private ValidatableResponse response;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Parameterized.Parameters()
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {new User().setRandomEmail().setRandomName()},
                        {new User().setRandomPassword().setRandomEmail()},
                        {new User().setRandomPassword().setRandomName()},
                }
        );
    }

    public LoginWithoutMandatoryFieldsTest(User body) {
        this.body = body;
    }

    @Test
    @Story("Логин пользователя в системе")
    @Description("Логин пользователя в системе без обязательных полей")
    @DisplayName("Логин пользователя в системе без обязательных полей")
    public void checkNewCourierCanNotLoginWithoutMandatoryFields() {

        response = userClient.login(body);

        System.out.println(response);
        response.assertThat().statusCode(SC_UNAUTHORIZED);
        response.assertThat().extract().path("message").equals("email or password are incorrect");
    }
}