package com.phptravelsnative.Models;

/**
 * Created by apple on 23/05/2017.
 */

public class seatModel {

    private String seat_on;
    private String seat_id;
    private String seat_status;

    public String getSeat_on() {
        return seat_on;
    }

    public void setSeat_on(String seat_on) {
        this.seat_on = seat_on;
    }

    public String getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(String seat_id) {
        this.seat_id = seat_id;
    }

    public String getSeat_status() {
        return seat_status;
    }

    public void setSeat_status(String seat_status) {
        this.seat_status = seat_status;
    }

    public String getSeat_fare() {
        return seat_fare;
    }

    public void setSeat_fare(String seat_fare) {
        this.seat_fare = seat_fare;
    }

    private String seat_fare;
}
