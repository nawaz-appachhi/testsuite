package com.myntra.apiTests.portalservices.lgpservices;

public enum ScenarioType {
	LOCATION,FOLLOWTOPIC;
	
	public static ScenarioType getValue(String value){
		ScenarioType[] elements =ScenarioType.values();
		for(ScenarioType element:elements){
			if(element.name().equalsIgnoreCase(value)){
				return(element);
			}
		}
		return(null);
	}
}
