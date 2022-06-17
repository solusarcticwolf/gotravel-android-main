package com.phptravelsnative.Models;

import com.google.gson.annotations.SerializedName;
import com.phptravelsnative.Models.RouteItem;

import java.io.Serializable;
import java.util.ArrayList;
@SuppressWarnings("serial") //With this annotation we are going to hide compiler warnings
public class DataItem implements Serializable {

    private ArrayList<RouteItem> routes;
    private ArrayList<RoutesItem> Route;
    String from_code ;
    String to_code ;
    String airline ;
    String currency ;
    String flight_duration ;

    public ArrayList<RoutesItem> getRoute() {
        return Route;
    }

    public void setRoute(ArrayList<RoutesItem> route) {
        Route = route;
    }

    String flight_id ;
    String flight_price ;
    String stops ;
    String departure_time ;
    String arrival_time ;
    @SerializedName("custom_payload")
    private CustomPayload customPayload;
    String adults,children ,infants,flight_type;

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getInfants() {
        return infants;
    }

    public void setInfants(String infants) {
        this.infants = infants;
    }

    public String getFlight_type() {
        return flight_type;
    }

    public void setFlight_type(String flight_type) {
        this.flight_type = flight_type;
    }

    public DataItem() {
    }

    public String getFrom_code() {
        return from_code;
    }

    public void setFrom_code(String from_code) {
        this.from_code = from_code;
    }

    public String getTo_code() {
        return to_code;
    }

    public void setTo_code(String to_code) {
        this.to_code = to_code;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFlight_duration() {
        return flight_duration;
    }

    public void setFlight_duration(String flight_duration) {
        this.flight_duration = flight_duration;
    }

    public String getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(String flight_id) {
        this.flight_id = flight_id;
    }

    public String getFlight_price() {
        return flight_price;
    }

    public void setFlight_price(String flight_price) {
        this.flight_price = flight_price;
    }

    public String getStops() {
        return stops;
    }

    public void setStops(String stops) {
        this.stops = stops;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public void setRoutes(ArrayList<RouteItem> routes){
        this.routes = routes;
    }

    public ArrayList<RouteItem> getRoutes(){
        return routes;
    }

    public void setCustomPayload(CustomPayload customPayload){
        this.customPayload = customPayload;
    }

    public CustomPayload getCustomPayload(){
        return customPayload;
    }
}
