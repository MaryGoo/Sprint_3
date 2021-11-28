package ru.praktikum_services.qa_scooter.CourierLogin;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.models.Courier;
import ru.praktikum_services.qa_scooter.models.CourierCredentials;

import java.util.Arrays;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static ru.praktikum_services.qa_scooter.utilities.Utilities.replace;

@RunWith(value = Parameterized.class)
public class loginWithoutMandatoryFieldsTest {
    private CourierClient courierClient;
    private String fieldName;
    private Courier courier;
    private int courierId;
    private ValidatableResponse response;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Parameterized.Parameters(name = "{index}: в теле запроса отсутствует поле: {0}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {"login"},
                        {"password"},
                }
        );
    }

    public loginWithoutMandatoryFieldsTest(String fieldName) {
        this.fieldName = fieldName;
    }

    @Test
    @Story("Логин курьера в системе")
    @Description("Логин курьера в системе без обязательных полей")
    @DisplayName("Логин курьера в системе без обязательных полей")
    public void checkNewCourierCanNotLoginWithoutMandatoryFields() {
        //Arrange
        replace(courier, fieldName, "");

        //Act
        response = courierClient.login(CourierCredentials.from(courier));

        //Assert
        response.assertThat().statusCode(SC_BAD_REQUEST);
        response.assertThat().extract().path("message").equals("Недостаточно данных для входа");
    }
}