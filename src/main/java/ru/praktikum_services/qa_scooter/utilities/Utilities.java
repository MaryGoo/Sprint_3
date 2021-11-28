package ru.praktikum_services.qa_scooter.utilities;

import ru.praktikum_services.qa_scooter.models.Courier;

public class Utilities {

    public static void replace(Courier courier, String fieldName, String newValue){
        switch (fieldName){
            case "login":{
                courier.setLogin(newValue);
                break;
            }
            case "password":{
                courier.setPassword(newValue);
                break;
            }
            default:{
                System.out.println("Nothing to replace for null. Check the field name in the attributes.");
            }
        }
    }
}