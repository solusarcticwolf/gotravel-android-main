package com.phptravelsnative.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by apple on 11/05/2017.
 */

public class BusSearchModel implements Parcelable {

    private String departureDate;
    private String from_id;
    private String to_id;
    private String seat_id;
    private String seat_status;
    private String Seat_No;
    private String fare;

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

    public String getSeat_No() {
        return Seat_No;
    }

    public void setSeat_No(String seat_No) {
        Seat_No = seat_No;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public BusSearchModel(Parcel in) {
        departureDate = in.readString();
        from_id = in.readString();
        to_id = in.readString();
        Seat_No = in.readString();
        seat_id = in.readString();
        fare = in.readString();
        seat_status = in.readString();
    }
    public BusSearchModel()
    {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(departureDate);
        dest.writeString(from_id);
        dest.writeString(to_id);
        dest.writeString(seat_id);
        dest.writeString(seat_status);
        dest.writeString(fare);
        dest.writeString(Seat_No);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BusSearchModel> CREATOR = new Creator<BusSearchModel>() {
        @Override
        public BusSearchModel createFromParcel(Parcel in) {
            return new BusSearchModel(in);
        }

        @Override
        public BusSearchModel[] newArray(int size) {
            return new BusSearchModel[size];
        }
    };

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
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
}
