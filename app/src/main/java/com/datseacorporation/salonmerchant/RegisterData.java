package com.datseacorporation.salonmerchant;

public class RegisterData {

    private String salonname, salonstate, saloncity, streetaddress, mobilenumber, salontype, totalemployees;

    RegisterData(){

    }

    public RegisterData(String salonname, String salonstate, String saloncity, String streetaddress, String mobilenumber, String salontype, String totalemployees) {
        this.salonname = salonname;
        this.salonstate = salonstate;
        this.saloncity = saloncity;
        this.streetaddress = streetaddress;
        this.mobilenumber = mobilenumber;
        this.salontype = salontype;
        this.totalemployees = totalemployees;
    }

    public String getSalonname() {
        return salonname;
    }

    public void setSalonname(String salonname) {
        this.salonname = salonname;
    }

    public String getSalonstate() {
        return salonstate;
    }

    public void setSalonstate(String salonstate) {
        this.salonstate = salonstate;
    }

    public String getSaloncity() {
        return saloncity;
    }

    public void setSaloncity(String saloncity) {
        this.saloncity = saloncity;
    }

    public String getStreetaddress() {
        return streetaddress;
    }

    public void setStreetaddress(String streetaddress) {
        this.streetaddress = streetaddress;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getSalontype() {
        return salontype;
    }

    public void setSalontype(String salontype) {
        this.salontype = salontype;
    }

    public String getTotalemployees() {
        return totalemployees;
    }

    public void setTotalemployees(String totalemployees) {
        this.totalemployees = totalemployees;
    }
}

