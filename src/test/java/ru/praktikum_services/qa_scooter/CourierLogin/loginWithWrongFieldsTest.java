package ru.praktikum_services.qa_scooter.CourierLogin;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
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

import static org.apache.http.HttpStatus.SC_NOT_FOUND;

@RunWith(value = Parameterized.class)
public class loginWithWrongFieldsTest {
    private CourierClient courierClient;
    private String fieldName;
    private Courier courier;
    private int courierId;
    private ValidatableResponse response;

    @Before
    public void precondition() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
    }

    @After
    public void tearDown(){
        courierClient.delete(courierId);
    }

    @Parameterized.Parameters(name = "{index}: в теле запроса в поле: {0} указано не верное значение")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {"login"},
                        {"password"},
                }
        );
    }

    public loginWithWrongFieldsTest(String fieldName) {
        this.fieldName = fieldName;
    }

    @Test
    @Story("Логин курьера в системе")
    @Description("Логин курьера в системе с не верным {fieldName}")
    @DisplayName("Логин курьера в системе с не верным {fieldName}")
    public void checkNewCourierCanNotLoginWithWrongFields() {
        //Arrange
        if (fieldName =="login"){
            courier.setLogin(RandomStringUtils.randomAlphabetic(15));
        } else if (fieldName == "password"){
            courier.setPassword(RandomStringUtils.randomAlphabetic(14));
        } else {
            System.out.println("Check the field name in the attributes.");
        }

        //Act
        response = courierClient.login(CourierCredentials.from(courier));

        //Assert
        response.assertThat().statusCode(SC_NOT_FOUND);
        response.assertThat().extract().path("message").equals("Учетная запись не найдена");
    }
}