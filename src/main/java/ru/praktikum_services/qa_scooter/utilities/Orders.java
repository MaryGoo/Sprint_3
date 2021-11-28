package ru.praktikum_services.qa_scooter.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.praktikum_services.qa_scooter.models.OrderFromDB;

import java.util.List;

public class Orders {

    public static  List<OrderFromDB> generateOrderFromDBList(List<Object> orderFromDBListObj) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(orderFromDBListObj);
        return gson.fromJson(json, new TypeToken<List<OrderFromDB>>() {
        }.getType());
    }
}