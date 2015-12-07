package com.snapdeal.disconsumer.enums;

public enum Actions {
	CalculateAndStore("Calculate And Store"),
	PushToSummaryProfile("Push To Summary Profile")
	;
	
	private final String description;
	
	Actions(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
}
