package ru.praktikum_services.qa_scooter.models;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Order {
    static Faker faker = new Faker(new Locale("ru_RU"));

    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public String deliveryDate;
    public int rentTime;
    public String[] color;
    public String comment;

    public Order(String firstName, String lastName, String address, String metroStation, String phone, String deliveryDate, int rentTime, String[] color, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.deliveryDate = deliveryDate;
        this.rentTime = rentTime;
        this.color = color;
        this.comment = comment;
    }

    public static Order getRandom() {
        final String firstName = faker.name().firstName();
        final String lastName = faker.name().lastName();
        final String address = faker.address().streetAddress();
        final String phone = "+7 903" + RandomStringUtils.random(6,"123456789");
        final String metroStation = RandomStringUtils.random(1,"123456789");
        final String deliveryDate = tomorrowWithYear();
        final int rentTime = Integer.parseInt(RandomStringUtils.random(1,"123456789"));
        final String comment = "Комментарий к заказу от " + deliveryDate;
        String[] color = {""};
        return new Order(firstName, lastName, address, metroStation, phone, deliveryDate, rentTime, color, comment);
    }

    static Calendar day = Calendar.getInstance();

    public static void tomorrow() {
        day.add(Calendar.DATE, 1);
    }

    public static String tomorrowWithYear() {
        tomorrow();
        return (new SimpleDateFormat("yyyy-MM-dd")).format(day.getTime());
    }

    public void setColor(String[] strings){
        this.color = strings;
    }
}