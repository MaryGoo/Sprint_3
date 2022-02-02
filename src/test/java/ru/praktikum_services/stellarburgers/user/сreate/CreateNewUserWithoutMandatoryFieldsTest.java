package ru.praktikum_services.stellarburgers.user.сreate;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.stellarburgers.client.UserClient;
import ru.praktikum_services.stellarburgers.model.User;

import java.util.Arrays;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;

@RunWith(value = Parameterized.class)
public class CreateNewUserWithoutMandatoryFieldsTest {
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

    public CreateNewUserWithoutMandatoryFieldsTest(User body) {
        this.body = body;
    }

    @Test
    @Story("Создание пользователя")
    @Description("Создание пользователя без обязательных полей невозможно")
    @DisplayName("Создание пользователя без обязательных полей невозможно")
    public void checkUserCanNotBeCreatedWithoutMandatoryFields() {
        User user = body;

        response = userClient.create(user);

        response.assertThat().statusCode(SC_FORBIDDEN);
        response.assertThat().extract().path("message").equals("Email, password and name are required fields");
    }
}