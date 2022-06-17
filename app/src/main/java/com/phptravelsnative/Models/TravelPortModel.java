package com.phptravelsnative.Models;

import java.util.ArrayList;

/**
 * Created by qasimhussain on 10/23/17.
 */

public class TravelPortModel {



     private  String aero_plane_number = "";
     private  String take_off_destination = "";
     private  String total_time = "";
     private  String toatl_stop = "";
     private  String arrival_time = "";
     private  String name_aero = "";
     private  String price = "";
     private  String img_aero = "";
     private  String currCode = "";
     private  String takeOff_time = "";
     private  String arrivalDestination = "";
     private  String b_takeOff_time = "";
     private  String b_arrival_time = "";
     private  String b_takeOffDestination = "";
     private  String b_toatl_time = "";
     private  String b_total_stop = "";
     private  String b_arrivalDestination = "";
     private  String b_aero_plane_number = "";
     private  boolean checkInsert = false;
     private ArrayList<TravelPortDetails> detials =new ArrayList<>();
     private ArrayList<TravelPortDetails> detials_inbounds =new ArrayList<>();
     private  boolean b_inbound = false;



    public String getAero_plane_number() {
        return aero_plane_number;
    }

    public void setAero_plane_number(String aero_plane_number) {
        this.aero_plane_number = aero_plane_number;
    }

    public String getTake_off_destination() {
        return take_off_destination;
    }

    public void setTake_off_destination(String take_off_destination) {
        this.take_off_destination = take_off_destination;
    }

    public String getTotal_time() {
        return total_time;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    public String getToatl_stop() {
        return toatl_stop;
    }

    public void setToatl_stop(String toatl_stop) {
        this.toatl_stop = toatl_stop;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getName_aero() {
        return name_aero;
    }

    public void setName_aero(String name_aero) {
        this.name_aero = name_aero;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg_aero() {
        return img_aero;
    }

    public void setImg_aero(String img_aero) {
        this.img_aero = img_aero;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    public String getTakeOff_time() {
        return takeOff_time;
    }

    public void setTakeOff_time(String takeOff_time) {
        this.takeOff_time = takeOff_time;
    }

    public String getArrivalDestination() {
        return arrivalDestination;
    }

    public void setArrivalDestination(String arrivalDestination) {
        this.arrivalDestination = arrivalDestination;
    }

    public String getB_takeOff_time() {
        return b_takeOff_time;
    }

    public void setB_takeOff_time(String b_takeOff_time) {
        this.b_takeOff_time = b_takeOff_time;
    }

    public String getB_arrival_time() {
        return b_arrival_time;
    }

    public void setB_arrival_time(String b_arrival_time) {
        this.b_arrival_time = b_arrival_time;
    }

    public String getB_takeOffDestination() {
        return b_takeOffDestination;
    }

    public void setB_takeOffDestination(String b_takeOffDestination) {
        this.b_takeOffDestination = b_takeOffDestination;
    }

    public String getB_toatl_time() {
        return b_toatl_time;
    }

    public void setB_toatl_time(String b_toatl_time) {
        this.b_toatl_time = b_toatl_time;
    }

    public String getB_total_stop() {
        return b_total_stop;
    }

    public void setB_total_stop(String b_total_stop) {
        this.b_total_stop = b_total_stop;
    }

    public String getB_arrivalDestination() {
        return b_arrivalDestination;
    }

    public void setB_arrivalDestination(String b_arrivalDestination) {
        this.b_arrivalDestination = b_arrivalDestination;
    }

    public String getB_aero_plane_number() {
        return b_aero_plane_number;
    }

    public void setB_aero_plane_number(String b_aero_plane_number) {
        this.b_aero_plane_number = b_aero_plane_number;
    }

    public boolean isCheckInsert() {
        return checkInsert;
    }

    public boolean getCheckInsert() {
        return checkInsert;
    }

    public void setCheckInsert(boolean checkInsert) {
        this.checkInsert = checkInsert;
    }

    public ArrayList<TravelPortDetails> getDetials() {
        return detials;
    }


    public ArrayList<TravelPortDetails> getDetials_inbounds() {
        return detials_inbounds;
    }

    public void setDetials_inbounds(ArrayList<TravelPortDetails> detials_inbounds) {
        this.detials_inbounds = detials_inbounds;
    }

    public boolean isB_inbound() {
        return b_inbound;
    }

    public void setB_inbound(boolean b_inbound) {
        this.b_inbound = b_inbound;
    }


    public void setDetials(ArrayList<TravelPortDetails> detials) {
        this.detials = detials;
    }
}

