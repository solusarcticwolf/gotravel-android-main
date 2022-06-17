package com.phptravelsnative.Models;

/**
 * Created by qasimhussain on 10/23/17.
 */

public class TravelPortDetails {

    private String date_to = "";
    private String date_from = "";
    private String location_to = "";
    private String location_from = "";
    private String flight_type = "";
    private String time_from = "";
    private String time_to = "";
    private String check_inner_segment = "";
    private String check_header_segment = "";
    private boolean check_CheckUnChecked = false;
    private String key = "";

    public String getTime_to() {
        return time_to;
    }

    public void setTime_to(String time_to) {
        this.time_to = time_to;
    }

    public String getLocation_from() {
        return location_from;
    }

    public void setLocation_from(String location_from) {
        this.location_from = location_from;
    }
    public String getDate_to() {
        return date_to;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }

    public String getDate_from() {
        return date_from;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public String getLocation_to() {
        return location_to;
    }

    public void setLocation_to(String location_to) {
        this.location_to = location_to;
    }

    public String getFlight_type() {
        return flight_type;
    }

    public void setFlight_type(String flight_type) {
        this.flight_type = flight_type;
    }

    public String getTime_from() {
        return time_from;
    }

    public void setTime_from(String time_from) {
        this.time_from = time_from;
    }

    public String getCheck_inner_segment() {
        return check_inner_segment;
    }

    public void setCheck_inner_segment(String check_inner_segment) {
        this.check_inner_segment = check_inner_segment;
    }

    public String getCheck_header_segment() {
        return check_header_segment;
    }

    public void setCheck_header_segment(String check_header_segment) {
        this.check_header_segment = check_header_segment;
    }

    public boolean isCheck_CheckUnChecked() {
        return check_CheckUnChecked;
    }

    public void setCheck_CheckUnChecked(boolean check_CheckUnChecked) {
        this.check_CheckUnChecked = check_CheckUnChecked;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
