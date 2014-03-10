package org.niklas.vaadininvoice.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Address {
	@Size(min = 5, max = 50)
	private String name;
	private String name2;
	private String address;
	private String postcode;
	private String city;
	private String country;

	public Address(){
		name = "";
		name2 = "";
		address = "";
		postcode = "";
		city = "";
		country = "";
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
