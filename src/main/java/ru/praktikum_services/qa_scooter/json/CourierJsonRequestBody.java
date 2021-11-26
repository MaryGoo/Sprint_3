package ru.praktikum_services.qa_scooter.json;

import ru.praktikum_services.qa_scooter.models.Courier;

public class CourierJsonRequestBody {

    public static String courierCreate(Courier courier) {
        return  "{\"login\":\"" + courier.getLogin() + "\","
                + "\"password\":\"" + courier.getPassword() + "\","
                + "\"firstName\":\"" + courier.getFirstName() + "\"}";
    }



    public static String courierLoginBodyWithoutSomeField(Courier courier, String fieldName){
        switch (fieldName){
            case "login":{
                return  "{\"login\":\"\","
                        + "\"password\":\"" + courier.getPassword() + "\"}";
            }
            case "password":{
                return  "{\"login\":\"" + courier.getLogin() + "\","
                        + "\"password\":\"\"}";
            }
            default: {
                System.out.println("Не верно указано поле в теле запроса, которое не надо заполнять");
                return null;
            }
        }
    }
}