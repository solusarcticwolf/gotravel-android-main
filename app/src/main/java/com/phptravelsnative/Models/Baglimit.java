package com.phptravelsnative.Models;


import com.google.gson.annotations.SerializedName;

public class Baglimit{

	@SerializedName("hand_length")
	private int handLength;

	@SerializedName("hand_weight")
	private int handWeight;

	@SerializedName("hand_height")
	private int handHeight;

	@SerializedName("hand_width")
	private int handWidth;

	@SerializedName("hold_weight")
	private int holdWeight;

	public void setHandLength(int handLength){
		this.handLength = handLength;
	}

	public int getHandLength(){
		return handLength;
	}

	public void setHandWeight(int handWeight){
		this.handWeight = handWeight;
	}

	public int getHandWeight(){
		return handWeight;
	}

	public void setHandHeight(int handHeight){
		this.handHeight = handHeight;
	}

	public int getHandHeight(){
		return handHeight;
	}

	public void setHandWidth(int handWidth){
		this.handWidth = handWidth;
	}

	public int getHandWidth(){
		return handWidth;
	}

	public void setHoldWeight(int holdWeight){
		this.holdWeight = holdWeight;
	}

	public int getHoldWeight(){
		return holdWeight;
	}

	@Override
 	public String toString(){
		return 
			"Baglimit{" + 
			"hand_length = '" + handLength + '\'' + 
			",hand_weight = '" + handWeight + '\'' + 
			",hand_height = '" + handHeight + '\'' + 
			",hand_width = '" + handWidth + '\'' + 
			",hold_weight = '" + holdWeight + '\'' + 
			"}";
		}
}