package com.phptravelsnative.Models;

public class flight_data {

   String  operating_airline_iata ,operating_airline_name,from_city,from_code,to_code,departure_time,to_city,arrival_time,
           price,flight_id,timestamp,to_country,from_country,to_station,from_station,flight_no;

    public flight_data(String operating_airline_iata, String operating_airline_name, String from_city, String from_code, String to_code, String departure_time, String to_city, String arrival_time, String price, String flight_id, String timestamp, String to_country, String from_country, String to_station, String from_station, String flight_no) {
        this.operating_airline_iata = operating_airline_iata;
        this.operating_airline_name = operating_airline_name;
        this.from_city = from_city;
        this.from_code = from_code;
        this.to_code = to_code;
        this.departure_time = departure_time;
        this.to_city = to_city;
        this.arrival_time = arrival_time;
        this.price = price;
        this.flight_id = flight_id;
        this.timestamp = timestamp;
        this.to_country = to_country;
        this.from_country = from_country;
        this.to_station = to_station;
        this.from_station = from_station;
        this.flight_no = flight_no;
    }

    public String getFrom_code() {
        return from_code;
    }

    public void setFrom_code(String from_code) {
        this.from_code = from_code;
    }

    public flight_data() {
    }

    public String getOperating_airline_iata() {
        return operating_airline_iata;
    }

    public void setOperating_airline_iata(String operating_airline_iata) {
        this.operating_airline_iata = operating_airline_iata;
    }

    public String getOperating_airline_name() {
        return operating_airline_name;
    }

    public void setOperating_airline_name(String operating_airline_name) {
        this.operating_airline_name = operating_airline_name;
    }

    public String getFrom_city() {
        return from_city;
    }

    public void setFrom_city(String from_city) {
        this.from_city = from_city;
    }

    public String getTo_code() {
        return to_code;
    }

    public void setTo_code(String to_code) {
        this.to_code = to_code;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getTo_city() {
        return to_city;
    }

    public void setTo_city(String to_city) {
        this.to_city = to_city;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(String flight_id) {
        this.flight_id = flight_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTo_country() {
        return to_country;
    }

    public void setTo_country(String to_country) {
        this.to_country = to_country;
    }

    public String getFrom_country() {
        return from_country;
    }

    public void setFrom_country(String from_country) {
        this.from_country = from_country;
    }

    public String getTo_station() {
        return to_station;
    }

    public void setTo_station(String to_station) {
        this.to_station = to_station;
    }

    public String getFrom_station() {
        return from_station;
    }

    public void setFrom_station(String from_station) {
        this.from_station = from_station;
    }

    public String getFlight_no() {
        return flight_no;
    }

    public void setFlight_no(String flight_no) {
        this.flight_no = flight_no;
    }
}
