package com.myntra.apiTests;

public enum JSONTYPE {
	
	JSONOBJECT("JsonObject"),
	JSONARRAY("JsonArray"),
	JSONPRIMITIVE("JsonPrimitive");
	
	public String jsonType;
	private JSONTYPE(String cJsonType){
		
		this.jsonType = cJsonType;
		
	}

}
