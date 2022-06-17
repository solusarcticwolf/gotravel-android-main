package com.phptravelsnative.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by qasimhussain on 10/26/17.
 */

public class TravelPort implements Parcelable {

    private String adults = "";
    private String child = "";
    private String infants = "";
    private String from_date = "";
    private String to_date = "";
    private String from_id = "";
    private String to_id = "";
    private String class_type = "";
    private String tour_type = "";

    public TravelPort(Parcel in) {
        adults = in.readString();
        child = in.readString();
        infants = in.readString();
        from_date = in.readString();
        to_date = in.readString();
        from_id = in.readString();
        to_id = in.readString();
        class_type = in.readString();
        tour_type = in.readString();
    }

    public TravelPort() {
    }

    public static final Creator<TravelPort> CREATOR = new Creator<TravelPort>() {
        @Override
        public TravelPort createFromParcel(Parcel in) {
            return new TravelPort(in);
        }

        @Override
        public TravelPort[] newArray(int size) {
            return new TravelPort[size];
        }
    };

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getInfants() {
        return infants;
    }

    public void setInfants(String infants) {
        this.infants = infants;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public String getClass_type() {
        return class_type;
    }

    public void setClass_type(String class_type) {
        this.class_type = class_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTour_type() {
        return tour_type;
    }

    public void setTour_type(String tour_type) {
        this.tour_type = tour_type;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(adults);
        dest.writeString(child);
        dest.writeString(infants);
        dest.writeString(from_date);
        dest.writeString(to_date);
        dest.writeString(from_id);
        dest.writeString(to_id);
        dest.writeString(class_type);
        dest.writeString(tour_type);
    }
}
