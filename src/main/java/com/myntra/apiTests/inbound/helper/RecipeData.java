package com.myntra.apiTests.inbound.helper;

import com.google.gson.annotations.SerializedName;

public class RecipeData {
	
	// @SerializedName("Colour Name")
	 private String baseColour;
	 
	// @SerializedName("Collar Type")
	    private String collarType;

		public String getBaseColour() {
			return baseColour;
		}

		public void setBaseColour(String baseColour) {
			this.baseColour = baseColour;
		}

		public String getCollarType() {
			return collarType;
		}

		public void setCollarType(String collarType) {
			this.collarType = collarType;
		}

	   

}
