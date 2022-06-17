package com.phptravelsnative.Models;

import java.util.ArrayList;

public class Model_Room {



    String rate,hotelname;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    String address,latitude,longitude,checkin,cehckout;
    private String id;
    private String price;
    private String hotel_id;
    private String room_descriptions;
    ArrayList < String > image = new ArrayList < String > ();
    private String room_name;
    private String room_slug;
    private String adults;
    private String childs;
    private String type;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCehckout() {
        return cehckout;
    }

    public void setCehckout(String cehckout) {
        this.cehckout = cehckout;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }



    public String getPrice() {
        return price;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public String getRoom_descriptions() {
        return room_descriptions;
    }

    public String getRoom_name() {
        return room_name;
    }

    public String getRoom_slug() {
        return room_slug;
    }



    public String getAdults() {
        return adults;
    }

    public String getChilds() {
        return childs;
    }

    public String getType() {
        return type;
    }

    // Setter Methods



    public void setId(String id) {
        this.id = id;
    }



    public void setPrice(String price) {
        this.price = price;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public void setRoom_descriptions(String room_descriptions) {
        this.room_descriptions = room_descriptions;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public void setRoom_slug(String room_slug) {
        this.room_slug = room_slug;
    }



    public void setAdults(String adults) {
        this.adults = adults;
    }

    public void setChilds(String childs) {
        this.childs = childs;
    }

    public void setType(String type) {
        this.type = type;
    }
}
