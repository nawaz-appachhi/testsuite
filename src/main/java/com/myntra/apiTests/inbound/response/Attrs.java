package com.myntra.apiTests.inbound.response;

public class Attrs {
	private String base_colour;
	private String season;
	private String brand;
	private String ALL;
	private String size;

	public String getBase_colour() {
		return base_colour;
	}

	public void setBase_colour(String base_colour) {
		this.base_colour = base_colour;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getALL() {
		return ALL;
	}

	public void setALL(String ALL) {
		this.ALL = ALL;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "ClassPojo [base_colour = " + base_colour + ", season = " + season + ", brand = " + brand + ", ALL = "
				+ ALL + ", size = " + size + "]";
	}

}
