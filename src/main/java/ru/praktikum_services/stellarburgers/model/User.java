package ru.praktikum_services.stellarburgers.model;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

public class User {

    public String email;
    public String password;
    public String name;

    static Faker faker = new Faker(new Locale("en_EN"));

    public User(){}

    public User(String email,String password,String name ){
        this.name= name;
        this.email = email;
        this.password = password;
    }

    public static User getRandom() {
        return new User().setRandomEmail().setRandomPassword().setRandomName();
    }

    public User setRandomEmail() {
        this.email = RandomStringUtils.randomAlphabetic(7) + "@yandex.ru";
        return this;
    }

    public User setRandomPassword(){
        this.password = RandomStringUtils.randomAlphabetic(10);
        return this;
    }

    public  User setRandomName(){
        this.name = faker.name().username();
        return this;
    }
}