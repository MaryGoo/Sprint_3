package ru.praktikum_services.qa_scooter.models;

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {

    public String login;
    public String password;
    public String firstName;

    public Courier(String login, String password, String firstName) {
        this.firstName = firstName;
        this.login = login;
        this.password = password;
    }

    public static Courier getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = setCourierFirstName();
        return new Courier(login, password, firstName);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    private static String setCourierFirstName() {
        String[] listFirstName = {"Евгения", "Екатерина", "Елена", "Епистима",
                "Алексей", "Василий", "Влас", "Демьян", "Гордей", "Евгений", "Кузьма", "Макар", "Николай", "Прохор",
                "Виталий", "Виктор", "Дементий", "Кондрат", "Константин", "Игнат", "Максим", "Сергей",
                "Елисей", "Захар", "Илья", "Матвей", "Михаил", "Семен", "Фома"};
        int f = (int) (Math.random() * listFirstName.length);
        return listFirstName[f];
    }
}