package ru.praktikum_services.qa_scooter.models;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Order {
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
        final String firstName = setName();
        final String lastName = setLastName();
        final String address = setAddress();
        final String phone = "+7 903" + RandomStringUtils.random(6,"123456789");
        final String metroStation = RandomStringUtils.random(1,"123456789");
        final String deliveryDate = tomorrowWithYear();
        final int rentTime = Integer.parseInt(RandomStringUtils.random(1,"123456789"));
        final String comment = "Комментарий к заказу от " + deliveryDate;
        String[] color = {""};
        return new Order(firstName, lastName, address, metroStation, phone, deliveryDate, rentTime, color, comment);
    }

    static Calendar day = Calendar.getInstance();

    public static String setName() {
        String[] listFirstName = {"Евгения", "Екатерина", "Елена", "Епистима",
                "Алексей", "Василий", "Влас", "Демьян", "Гордей", "Евгений", "Кузьма", "Макар", "Николай", "Прохор",
                "Виталий", "Виктор", "Дементий", "Кондрат", "Константин", "Игнат", "Максим", "Сергей",
                "Елисей", "Захар", "Илья", "Матвей", "Михаил", "Семен", "Фома"};
        int f = (int) (Math.random() * listFirstName.length);
        return listFirstName[f];
    }

    public static String setLastName() {
        String[] listLastName = {"Князев", "Беспалов", "Уваров", "Шашков", "Бобылёв", "Рожков", "Сысоев", "Селиверстов",
                "Иванко", "Петров", "Акуленко", "Кучкудуг", "Питерсон", "Пугачев", "Перегон", "Вейдер", "Степанко", "Куликов", "Мишустин"};
        int f = (int) (Math.random() * listLastName.length);
        return listLastName[f];
    }

    public static String setAddress() {
        String[] subwayStations = {"Чистые пруды", "Бульвар Рокоссовского", "Волоколамская", "Красные Ворота", "Библиотека имени Ленина"};
        int f = (int) (Math.random() * subwayStations.length);
        return subwayStations[f] + "дом 4, кв 2";
    }

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