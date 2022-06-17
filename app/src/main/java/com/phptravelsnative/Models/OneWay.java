package com.phptravelsnative.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class OneWay implements Parcelable {

    private String code = "";
    private String name = "";
    private String date = "";
    private String time = "";

    private String to_code = "";
    private String to_name = "";
    private String to_date = "";
    private String to_time = "";
    private String flight_no = "";
    private String img = "";

    public OneWay() {

    }

    protected OneWay(Parcel in) {
        code = in.readString();
        name = in.readString();
        date = in.readString();
        time = in.readString();
        to_code = in.readString();
        to_name = in.readString();
        to_date = in.readString();
        to_time = in.readString();
        flight_no = in.readString();
        img = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(to_code);
        dest.writeString(to_name);
        dest.writeString(to_date);
        dest.writeString(to_time);
        dest.writeString(flight_no);
        dest.writeString(img);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OneWay> CREATOR = new Creator<OneWay>() {
        @Override
        public OneWay createFromParcel(Parcel in) {
            return new OneWay(in);
        }

        @Override
        public OneWay[] newArray(int size) {
            return new OneWay[size];
        }
    };

    public String getTo_code() {
        return to_code;
    }

    public void setTo_code(String to_code) {
        this.to_code = to_code;
    }

    public String getTo_name() {
        return to_name;
    }

    public void setTo_name(String to_name) {
        this.to_name = to_name;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getTo_time() {
        return to_time;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setFlight_no(String flight_no) {
        this.flight_no = flight_no;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getFlight_no() {
        return flight_no;
    }

    public String getImg() {
        return img;
    }
}
