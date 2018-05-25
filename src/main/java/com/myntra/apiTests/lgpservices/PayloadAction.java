package com.myntra.apiTests.lgpservices;

public enum PayloadAction {
	
	REGISTEROBJECT,PUBLISHOBJECT,SERVEASSERTION;
	
	
	public static PayloadAction getValue(String value){
		PayloadAction[] elements =PayloadAction.values();
		for(PayloadAction element:elements){
			if(element.name().equalsIgnoreCase(value)){
				return(element);
			}
		}
		return(null);
	}

}
