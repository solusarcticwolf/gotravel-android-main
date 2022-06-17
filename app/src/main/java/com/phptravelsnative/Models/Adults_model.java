package com.phptravelsnative.Models;

public class Adults_model {


    String title,name,surname,email,phone,birthday,expiration,cardno,nationality,hold_bags,category;

    public Adults_model() {
    }

    public Adults_model(String title, String name, String surname, String email, String phone, String birthday, String expiration, String cardno, String nationality, String hold_bags, String category) {
        this.title = title;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.expiration = expiration;
        this.cardno = cardno;
        this.nationality = nationality;
        this.hold_bags = hold_bags;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getHold_bags() {
        return hold_bags;
    }

    public void setHold_bags(String hold_bags) {
        this.hold_bags = hold_bags;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
