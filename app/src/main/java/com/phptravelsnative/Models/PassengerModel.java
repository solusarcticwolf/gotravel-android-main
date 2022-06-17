package com.phptravelsnative.Models;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by qasimhussain on 10/30/17.
 */

public class PassengerModel implements Parcelable{

    public String title = "";
    public String mr = "";
    public String input_name = "";
    public String input_phone = "";
    public String input_email = "";
    public String first_name = "";
    public String nationality = "";
    public String cdn = "";


    public PassengerModel(){

    }
    protected PassengerModel(Parcel in) {
        title = in.readString();
        mr = in.readString();
        input_name = in.readString();
        input_phone = in.readString();
        input_email = in.readString();
        first_name = in.readString();
        nationality = in.readString();
        cdn = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(mr);
        dest.writeString(input_name);
        dest.writeString(input_phone);
        dest.writeString(input_email);
        dest.writeString(first_name);
        dest.writeString(nationality);
        dest.writeString(cdn);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PassengerModel> CREATOR = new Creator<PassengerModel>() {
        @Override
        public PassengerModel createFromParcel(Parcel in) {
            return new PassengerModel(in);
        }

        @Override
        public PassengerModel[] newArray(int size) {
            return new PassengerModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMr() {
        return mr;
    }

    public void setMr(String mr) {
        this.mr = mr;
    }

    public String getInput_name() {
        return input_name;
    }

    public void setInput_name(String input_name) {
        this.input_name = input_name;
    }

    public String getInput_phone() {
        return input_phone;
    }

    public void setInput_phone(String input_phone) {
        this.input_phone = input_phone;
    }

    public String getInput_email() {
        return input_email;
    }

    public void setInput_email(String input_email) {
        this.input_email = input_email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }
}
