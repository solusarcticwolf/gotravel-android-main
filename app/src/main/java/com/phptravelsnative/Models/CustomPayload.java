package com.phptravelsnative.Models;


import com.google.gson.annotations.SerializedName;


public class CustomPayload{

	@SerializedName("booking_token")
	private String bookingToken;

	@SerializedName("visitor_uniqid")
	private String visitorUniqid;

	public void setBookingToken(String bookingToken){
		this.bookingToken = bookingToken;
	}

	public String getBookingToken(){
		return bookingToken;
	}

	public void setVisitorUniqid(String visitorUniqid){
		this.visitorUniqid = visitorUniqid;
	}

	public String getVisitorUniqid(){
		return visitorUniqid;
	}

	@Override
 	public String toString(){
		return 
			"CustomPayload{" + 
			"booking_token = '" + bookingToken + '\'' + 
			",visitor_uniqid = '" + visitorUniqid + '\'' + 
			"}";
		}
}