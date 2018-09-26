package com.datseacorporation.salonmerchant;

public class ServicesData {

    String servicename, serviceduration, servicecost, servicedesc;
    int serviceid;
    ServicesData(){

    }

    public ServicesData(String servicename, String serviceduration, String servicecost, String servicedesc, int serviceid) {
        this.servicename = servicename;
        this.serviceduration = serviceduration;
        this.servicecost = servicecost;
        this.servicedesc = servicedesc;
        this.serviceid = serviceid;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getServiceduration() {
        return serviceduration;
    }

    public void setServiceduration(String serviceduration) {
        this.serviceduration = serviceduration;
    }

    public String getServicecost() {
        return servicecost;
    }

    public void setServicecost(String servicecost) {
        this.servicecost = servicecost;
    }

    public String getServicedesc() {
        return servicedesc;
    }

    public void setServicedesc(String servicedesc) {
        this.servicedesc = servicedesc;
    }

    public int getServiceid() {
        return serviceid;
    }

    public void setServiceid(int serviceid) {
        this.serviceid = serviceid;
    }
}
