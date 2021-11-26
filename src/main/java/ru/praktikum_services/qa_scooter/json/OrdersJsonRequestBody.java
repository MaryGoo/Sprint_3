package ru.praktikum_services.qa_scooter.json;

import ru.praktikum_services.qa_scooter.models.Customer;
import ru.praktikum_services.qa_scooter.models.Order;

public class OrdersJsonRequestBody {

    public static String ordersCreate(Customer customer, Order order,String fieldCount) {
        String body="{\"firstName\":\"" + customer.getFirstName() + "\","
                + "\"lastName\":\"" + customer.getLastName() + "\","
                + "\"address\":\"" + customer.getAddress() + "\","
                + "\"metroStation\":" + customer.getSubwayStation() + ","
                + "\"phone\":\"" + customer.getPhone() + "\","
                + "\"rentTime\":" + order.getRentalPeriod() + ","
                + "\"deliveryDate\":\"" + order.getDeliveryDateWithYear() + "\","
                + "\"comment\":\"" + order.getComment();
        switch (fieldCount) {
            case "all":{
                return body + "\","
                        + "\"color\":[\"" + order.getColor() + "\"]}";
            }
            case "mandatory":{
                return body + "\"}";
            }
            default: {
                System.out.println("Не верно указан атрибут метода");
                return null;
            }
        }
    }
}
