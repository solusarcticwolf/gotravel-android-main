package com.phptravelsnative.Models;

public class Flights
{

    private String from_country;

    private String arrival_time;

    private String from_code;

    private String from_city;



    String iata ,name;

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String to_country;

    private String from_station;

    private String to_city;

    private String arrival_utc_time;

    private String flight_id;

    private String departure_utc_time;

    private String flight_no;

    private String to_code;

    private String departure_time;


    private String to_station;

    public String getFrom_country ()
    {
        return from_country;
    }

    public void setFrom_country (String from_country)
    {
        this.from_country = from_country;
    }

    public String getArrival_time ()
    {
        return arrival_time;
    }

    public void setArrival_time (String arrival_time)
    {
        this.arrival_time = arrival_time;
    }

    public String getFrom_code ()
    {
        return from_code;
    }

    public void setFrom_code (String from_code)
    {
        this.from_code = from_code;
    }

    public String getFrom_city ()
    {
        return from_city;
    }

    public void setFrom_city (String from_city)
    {
        this.from_city = from_city;
    }


    public String getTo_country ()
    {
        return to_country;
    }

    public void setTo_country (String to_country)
    {
        this.to_country = to_country;
    }

    public String getFrom_station ()
    {
        return from_station;
    }

    public void setFrom_station (String from_station)
    {
        this.from_station = from_station;
    }

    public String getTo_city ()
    {
        return to_city;
    }

    public void setTo_city (String to_city)
    {
        this.to_city = to_city;
    }

    public String getArrival_utc_time ()
    {
        return arrival_utc_time;
    }

    public void setArrival_utc_time (String arrival_utc_time)
    {
        this.arrival_utc_time = arrival_utc_time;
    }


    public void setFlight_id (String flight_id)
    {
        this.flight_id = flight_id;
    }


    public void setDeparture_utc_time (String departure_utc_time)
    {
        this.departure_utc_time = departure_utc_time;
    }

    public void setFlight_no (String flight_no)
    {
        this.flight_no = flight_no;
    }
    public String getTo_code ()
    {
        return to_code;
    }

    public String getFlight_id() {
        return flight_id;
    }

    public String getDeparture_utc_time() {
        return departure_utc_time;
    }

    public String getFlight_no() {
        return flight_no;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public String getTo_station() {
        return to_station;
    }

    public void setTo_code (String to_code)
    {
        this.to_code = to_code;
    }

    public void setDeparture_time (String departure_time)
    {
        this.departure_time = departure_time;
    }
    public void setTo_station (String to_station)
    {
        this.to_station = to_station;
    }


}