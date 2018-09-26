package com.datseacorporation.salonmerchant;

public class AppointData {

    private String display_name, gender, time, bookingID, status, cost;

    AppointData(){

    }

    public AppointData(String display_name, String gender, String time, String bookingID, String status, String cost) {
        this.display_name = display_name;
        this.gender = gender;
        this.time = time;
        this.bookingID = bookingID;
        this.status = status;
        this.cost = cost;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
