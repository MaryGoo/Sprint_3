package ru.praktikum_services.qa_scooter.models;

import com.google.gson.annotations.Expose;

public class OrderFromDB {
    @Expose
    public int id;
    @Expose
    public int courierId;
    @Expose
    public String firstName;
    @Expose
    public String lastName;
    @Expose
    public String address;
    @Expose
    public String metroStation;
    @Expose
    public String phone;
    @Expose
    public int rentTime;
    @Expose
    public String deliveryDate;
    @Expose
    public int track;
    @Expose
    public String[] color = new String[2];
    @Expose
    public String comment;
    @Expose
    public String createdAt;
    @Expose
    public String updatedAt;
    @Expose
    public int status;


    public int getId() {
        return id;
    }

    public int getCourierId() {
        return courierId;
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

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public int getTrack() {
        return track;
    }

    public String[] getColor() {
        return color;
    }

    public String getComment() {
        return comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getStatus() {
        return status;
    }

}