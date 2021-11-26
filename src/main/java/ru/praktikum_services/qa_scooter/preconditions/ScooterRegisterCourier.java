package ru.praktikum_services.qa_scooter.preconditions;
// импортируем RestAssured
// импортируем Response
import io.restassured.response.Response;
// импортируем библиотеку генерации строк
        import ru.praktikum_services.qa_scooter.models.Courier;
// импортируем список
// дополнительный статический импорт нужен, чтобы использовать given(), get() и then()
import static io.restassured.RestAssured.*;
import static ru.praktikum_services.qa_scooter.apiData.EndPoints.ORDERS_URL;
import static ru.praktikum_services.qa_scooter.json.CourierJsonRequestBody.courierCreate;

public class ScooterRegisterCourier {

    public static boolean registerNewCourier(Courier courier){
        String registerRequestBody = courierCreate(courier);

        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(registerRequestBody)
                .when()
                .post(ORDERS_URL);

        if (response.statusCode() == 201) {
            System.out.println("Пользователь: "+courier.getFirstName() +" с логином: "+courier.getLogin()+" и с паролем: "+courier.getPassword() +" зарегистрирован успешно");
            return true;
        } else {
            return false;
        }
    }


//    public static boolean deleteCourier(Courier courier){
//
//    }
}