package com.phptravelsnative.Models;
import com.google.gson.annotations.SerializedName;


public class RouteItem{





	@SerializedName("longitude_from")
	private String longitudeFrom;




	@SerializedName("map_from")
	private String mapFrom;

	@SerializedName("arrival_utc_time")
	private int arrivalUtcTime;

	@SerializedName("flight_no")
	private String flightNo;

	@SerializedName("price")
	private String price;


	@SerializedName("to_code")
	private String toCode;

	@SerializedName("airline_type")
	private String airlineType;

	@SerializedName("airline")
	private String airline;

	@SerializedName("departure_time")
	private String departureTime;

	@SerializedName("city_to")
	private String cityTo;

	@SerializedName("arrival_time")
	private String arrivalTime;

	@SerializedName("from_code")
	private String fromCode;

	@SerializedName("latTo")
	private String latTo;

	@SerializedName("vehicle_type")
	private String vehicleType;

	@SerializedName("latitude_from")
	private String latitudeFrom;

	@SerializedName("departure_utc_time")
	private int departureUtcTime;

	@SerializedName("longitude_to")
	private String longitudeTo;

	@SerializedName("city_from")
	private String cityFrom;

	@SerializedName("map_to")
	private String mapTo;

	@SerializedName("latitude_to")
	private String latitudeTo;

	@SerializedName("original_return")
	private String originalReturn;

	@SerializedName("return")
	private int jsonMemberReturn;



	public void setLongitudeFrom(String longitudeFrom){
		this.longitudeFrom = longitudeFrom;
	}

	public String getLongitudeFrom(){
		return longitudeFrom;
	}





	public void setMapFrom(String mapFrom){
		this.mapFrom = mapFrom;
	}

	public String getMapFrom(){
		return mapFrom;
	}

	public void setArrivalUtcTime(int arrivalUtcTime){
		this.arrivalUtcTime = arrivalUtcTime;
	}

	public int getArrivalUtcTime(){
		return arrivalUtcTime;
	}



	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}



	public void setToCode(String toCode){
		this.toCode = toCode;
	}

	public String getToCode(){
		return toCode;
	}

	public void setAirlineType(String airlineType){
		this.airlineType = airlineType;
	}

	public String getAirlineType(){
		return airlineType;
	}

	public void setAirline(String airline){
		this.airline = airline;
	}

	public String getAirline(){
		return airline;
	}

	public void setDepartureTime(String departureTime){
		this.departureTime = departureTime;
	}

	public String getDepartureTime(){
		return departureTime;
	}

	public void setCityTo(String cityTo){
		this.cityTo = cityTo;
	}

	public String getCityTo(){
		return cityTo;
	}

	public void setArrivalTime(String arrivalTime){
		this.arrivalTime = arrivalTime;
	}

	public String getArrivalTime(){
		return arrivalTime;
	}

	public void setFromCode(String fromCode){
		this.fromCode = fromCode;
	}

	public String getFromCode(){
		return fromCode;
	}

	public void setLatTo(String latTo){
		this.latTo = latTo;
	}

	public String getLatTo(){
		return latTo;
	}

	public void setVehicleType(String vehicleType){
		this.vehicleType = vehicleType;
	}

	public String getVehicleType(){
		return vehicleType;
	}

	public void setLatitudeFrom(String latitudeFrom){
		this.latitudeFrom = latitudeFrom;
	}

	public String getLatitudeFrom(){
		return latitudeFrom;
	}

	public void setDepartureUtcTime(int departureUtcTime){
		this.departureUtcTime = departureUtcTime;
	}

	public int getDepartureUtcTime(){
		return departureUtcTime;
	}

	public void setLongitudeTo(String longitudeTo){
		this.longitudeTo = longitudeTo;
	}

	public String getLongitudeTo(){
		return longitudeTo;
	}

	public void setCityFrom(String cityFrom){
		this.cityFrom = cityFrom;
	}

	public String getCityFrom(){
		return cityFrom;
	}

	public void setMapTo(String mapTo){
		this.mapTo = mapTo;
	}

	public String getMapTo(){
		return mapTo;
	}

	public void setLatitudeTo(String latitudeTo){
		this.latitudeTo = latitudeTo;
	}

	public String getLatitudeTo(){
		return latitudeTo;
	}

	public void setOriginalReturn(String originalReturn){
		this.originalReturn = originalReturn;
	}

	public String getOriginalReturn(){
		return originalReturn;
	}

	public void setJsonMemberReturn(int jsonMemberReturn){
		this.jsonMemberReturn = jsonMemberReturn;
	}

	public int getJsonMemberReturn(){
		return jsonMemberReturn;
	}



	public void setFlightNo(String flightNo){
		this.flightNo = flightNo;
	}

	public String getFlightNo(){
		return flightNo;
	}

}