package com.phptravelsnative.Models;

import java.util.ArrayList;

public class Datafligh {


    ArrayList<Flights> flightsarrayLis =new ArrayList();

    private String mode;
    private String book_fee;
    private String total;
    private String currency;

  String from ,to ;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    private String booking_token;

    private String visitor_uniqid;


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getBook_fee() {
        return book_fee;
    }

    public void setBook_fee(String book_fee) {
        this.book_fee = book_fee;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBooking_token() {
        return booking_token;
    }

    public void setBooking_token(String booking_token) {
        this.booking_token = booking_token;
    }

    public String getVisitor_uniqid() {
        return visitor_uniqid;
    }

    public void setVisitor_uniqid(String visitor_uniqid) {
        this.visitor_uniqid = visitor_uniqid;
    }


    public ArrayList<Flights> getFlightsarrayLis() {
        return flightsarrayLis;
    }

    public void setFlightsarrayLis(ArrayList<Flights> flightsarrayLis) {
        this.flightsarrayLis = flightsarrayLis;
    }
}
