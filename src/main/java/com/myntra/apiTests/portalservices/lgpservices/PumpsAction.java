package com.myntra.apiTests.portalservices.lgpservices;

public enum PumpsAction {
	PUBLISHOBJECT,FOLLOW,FOLLOWTOPIC,REGISTEROBJECT;
	
	
	public static PumpsAction getValue(String value){
		PumpsAction[] elements =PumpsAction.values();
		for(PumpsAction element:elements){
			if(element.name().equalsIgnoreCase(value)){
				return(element);
			}
		}
		return(null);
	}
}
