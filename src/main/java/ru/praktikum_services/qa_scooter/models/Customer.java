package ru.praktikum_services.qa_scooter.models;

import java.util.Random;

public class Customer {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String subwayStation;

    public Customer() {
        this.firstName = setName();
        this.lastName = setLastName();
        this.phone = setPhone();
        this.address = setAddress();
        this.subwayStation = setSubwayStation();
    }

    private String setName() {
        String[] listFirstName = {"Евгения", "Екатерина", "Елена", "Епистима",
                "Алексей", "Василий", "Влас", "Демьян", "Гордей", "Евгений", "Кузьма", "Макар", "Николай", "Прохор",
                "Виталий", "Виктор", "Дементий", "Кондрат", "Константин", "Игнат", "Максим", "Сергей",
                "Елисей", "Захар", "Илья", "Матвей", "Михаил", "Семен", "Фома"};
        int f = (int) (Math.random() * listFirstName.length);
        return listFirstName[f];
    }

    private String setLastName() {
        String[] listLastName = {"Князев", "Беспалов", "Уваров", "Шашков", "Бобылёв", "Рожков", "Сысоев", "Селиверстов",
                "Иванко", "Петров", "Акуленко", "Кучкудуг", "Питерсон", "Пугачев", "Перегон", "Вейдер", "Степанко", "Куликов", "Мишустин"};
        int f = (int) (Math.random() * listLastName.length);
        return listLastName[f];
    }

    private String setPhone() {
        String s = "0123456789";
        StringBuffer number = new StringBuffer();
        for (int i = 0; i < 7; i++) {
            number.append(s.charAt(new Random().nextInt(s.length())));
        }
        return "8903" + number.toString();
    }

    private String setAddress() {
        return setSubwayStation() + "дом 4, кв 2";
    }

    private String setSubwayStation() {
        String[] subwayStations = {"Чистые пруды", "Бульвар Рокоссовского", "Волоколамская","Красные Ворота","Библиотека имени Ленина"};
        int f = (int) (Math.random() * subwayStations.length);
       // return subwayStations[f];
        return "4";
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getSubwayStation() {
        return subwayStation;
    }
}
