package com.phptravelsnative.Models;


import com.google.gson.annotations.SerializedName;

public class BagsPrice{

	@SerializedName("1")
	private double jsonMember1;

	@SerializedName("2")
	private int jsonMember2;

	public void setJsonMember1(double jsonMember1){
		this.jsonMember1 = jsonMember1;
	}

	public double getJsonMember1(){
		return jsonMember1;
	}

	public void setJsonMember2(int jsonMember2){
		this.jsonMember2 = jsonMember2;
	}

	public int getJsonMember2(){
		return jsonMember2;
	}

	@Override
 	public String toString(){
		return 
			"BagsPrice{" + 
			"1 = '" + jsonMember1 + '\'' + 
			",2 = '" + jsonMember2 + '\'' + 
			"}";
		}
}