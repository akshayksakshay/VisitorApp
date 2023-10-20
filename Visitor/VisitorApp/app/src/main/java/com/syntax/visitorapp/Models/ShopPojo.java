package com.syntax.visitorapp.Models;

public class ShopPojo {
    String sid;
    String name;
    String address;
    String location;
    String phone;
    String email;
    String date;
    String time;
    String  rp_name,rp_address,rp_details,rp_date,rp_postdate;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    ...

    public String getRp_name() {
        return rp_name;
    }

    public void setRp_name(String rp_name) {
        this.rp_name = rp_name;
    }

    public String getRp_address() {
        return rp_address;
    }

    public void setRp_address(String rp_address) {
        this.rp_address = rp_address;
    }

    public String getRp_details() {
        return rp_details;
    }

    public void setRp_details(String rp_details) {
        this.rp_details = rp_details;
    }

    public String getRp_date() {
        return rp_date;
    }

    public void setRp_date(String rp_date) {
        this.rp_date = rp_date;
    }

    public String getRp_postdate() {
        return rp_postdate;
    }

    public void setRp_postdate(String rp_postdate) {
        this.rp_postdate = rp_postdate;
    }
}
