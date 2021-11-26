package ru.praktikum_services.qa_scooter.CourierLogin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.models.Courier;
import ru.praktikum_services.qa_scooter.models.CourierCredentials;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.praktikum_services.qa_scooter.utilities.Utilities.replace;

@RunWith(value = Parameterized.class)
public class loginWithoutMandatoryFieldsTest {
    private CourierClient courierClient;
    private String fieldName;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));
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
    public void checkNewCourierCanNotLoginWithoutMandatoryFields() {
        //Arrange
        replace(courier, fieldName, "");

        //Act
        String messageAboutLogin = courierClient.loginWithoutMandatoryFields(CourierCredentials.from(courier));

        //Assert
        assertThat(messageAboutLogin, equalTo("Недостаточно данных для входа"));
    }
}