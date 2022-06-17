package com.phptravelsnative.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by apple on 11/05/2017.
 */

public class BusModel implements Parcelable {

    private String BusOperator;
    private String logo;
    private String fromCity;
    private String seatsLeft;


    public BusModel(){

    }

    protected BusModel(Parcel in) {
        BusOperator = in.readString();
        logo = in.readString();
        fromCity = in.readString();
        seatsLeft = in.readString();
        OppId = in.readString();
        sechdualID = in.readString();
        toCity = in.readString();
        departureTime = in.readString();
        arrivalTime = in.readString();
        price = in.readString();
    }

    public static final Creator<BusModel> CREATOR = new Creator<BusModel>() {
        @Override
        public BusModel createFromParcel(Parcel in) {
            return new BusModel(in);
        }

        @Override
        public BusModel[] newArray(int size) {
            return new BusModel[size];
        }
    };

    public String getOppId() {
        return OppId;
    }

    public void setOppId(String oppId) {
        OppId = oppId;
    }

    public String getSechdualID() {
        return sechdualID;
    }

    public void setSechdualID(String sechdualID) {
        this.sechdualID = sechdualID;
    }

    private String OppId;
    private String sechdualID;


    public String getSeatsLeft() {
        return seatsLeft;
    }

    public void setSeatsLeft(String seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    private String toCity;

    private String departureTime;
    private String arrivalTime;
    private String price;

    public String getBusOperator() {
        return BusOperator;
    }

    public void setBusOperator(String busOperator) {
        BusOperator = busOperator;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(BusOperator);
        dest.writeString(logo);
        dest.writeString(fromCity);
        dest.writeString(seatsLeft);
        dest.writeString(OppId);
        dest.writeString(sechdualID);
        dest.writeString(toCity);
        dest.writeString(departureTime);
        dest.writeString(arrivalTime);
        dest.writeString(price);
    }
}
