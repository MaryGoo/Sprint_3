package ru.praktikum_services.qa_scooter.OrdersCreate;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.models.Courier;
import ru.praktikum_services.qa_scooter.models.Customer;
import ru.praktikum_services.qa_scooter.models.Order;

import java.text.ParseException;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static ru.praktikum_services.qa_scooter.apiData.EndPoints.COURIER_URL;
import static ru.praktikum_services.qa_scooter.json.CourierJsonRequestBody.courierCreate;
import static ru.praktikum_services.qa_scooter.json.OrdersJsonRequestBody.ordersCreate;

@RunWith(value = Parameterized.class)
public class OrdersCreate {
//    String fieldCount;
//    String color;
//    Customer customer = new Customer();
//
//
//    @Parameterized.Parameters(name = "{index}: в теле запроса будут {0} поля.")
//    public static Iterable<Object[]> data() {
//        return Arrays.asList(new Object[][]{
//                        {"all", "BLACK"},
//                        {"mandatory","BLACK"},
//                }
//        );
//    }
//
//    public OrdersCreate(String fieldName, String color) throws ParseException {
//        this.fieldCount = fieldName;
//        this.color = color;
//    }
//
//    @Test
//    public void postCreateNewOrdersWithAllFields() throws ParseException {
//        Order order = new Order(color);
//        Response response =
//                given()
//                        .header("Content-type", "application/json")
//                        .and()
//                        .body(ordersCreate(customer,order,fieldCount))
//                        .when()
//                        .post(COURIER_URL);
//        System.out.println(ordersCreate(customer,order,fieldCount));
//        System.out.println(response.toString());
//        response.then().assertThat().body("track", notNullValue())
//                .and()
//                .statusCode(201);
//    }
}
