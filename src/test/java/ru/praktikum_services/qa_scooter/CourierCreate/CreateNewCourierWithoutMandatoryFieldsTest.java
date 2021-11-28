package ru.praktikum_services.qa_scooter.CourierCreate;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.models.Courier;

import java.util.Arrays;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static ru.praktikum_services.qa_scooter.utilities.Utilities.replace;


@RunWith(value = Parameterized.class)
public class CreateNewCourierWithoutMandatoryFieldsTest {
    private CourierClient courierClient;
    private String fieldName;
    private ValidatableResponse response;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
    }

    @Parameterized.Parameters(name = "{index}: в теле запроса отмутствует поле: {0}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"login"},
                {"password"},
                }
        );
    }

    public CreateNewCourierWithoutMandatoryFieldsTest(String fieldName){
        this.fieldName = fieldName;
    }

    @Test
    @Story("Создание курьера")
    @Description("Создание курьера без обязательных полей невозможно")
    @DisplayName("Создание курьера без обязательных полей невозможно")
    public void checkCourierCanNotBeCreatedWithoutMandatoryFields(){
        //Arrange
        Courier courier = Courier.getRandom();
        replace(courier,fieldName, null);

        //Act
        response = courierClient.create(courier);

        //Assert
        response.assertThat().statusCode(SC_BAD_REQUEST);
        response.assertThat().extract().path("message").equals("Недостаточно данных для создания учетной записи");
    }
}