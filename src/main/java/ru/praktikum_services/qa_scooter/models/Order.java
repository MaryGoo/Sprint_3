package ru.praktikum_services.qa_scooter.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Order {
    private String orderNumber;
    private String deliveryDate;
    private String deliveryDateWithYear;
    private String rentalPeriod;
    private String color;
    private String comment;
    Calendar day = Calendar.getInstance();

    public Order(String color) throws ParseException {
        deliveryDateWithYear = tomorrowWithYear();
        deliveryDate = tomorrowText(deliveryDateWithYear);
        this.color = color;
        comment = "Комментарий к заказу от " + deliveryDateWithYear;
        this.rentalPeriod = "5";
    }

//    private String chooseColor(String color) {
//        switch (color) {
//            case "black":
//                return "чёрный жемчуг";
//            case "grey":
//                return "серая безысходность";
//            default:
//                return null;
//        }
//    }

    public void tomorrow(){
        day.add(Calendar.DATE, 1);
    }

    public String tomorrowWithYear() {
        tomorrow();
        return (new SimpleDateFormat("yyyy-MM-dd")).format(day.getTime());
    }

    public String tomorrowText(String dataWithYear) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return (new SimpleDateFormat("dd MMMM")).format(format.parse(dataWithYear));
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryDateWithYear() {
        return deliveryDateWithYear;
    }

    public void setDeliveryDateWithYear(String deliveryDateWithYear) {
        this.deliveryDateWithYear = deliveryDateWithYear;
    }

    public String getRentalPeriod() {
        return rentalPeriod;
    }

    public void setRentalPeriod(String rentalPeriod) {
        this.rentalPeriod = rentalPeriod;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
