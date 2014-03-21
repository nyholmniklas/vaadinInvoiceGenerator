package org.niklas.vaadininvoice.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Address {
	@Size(min = 5, max = 40)
	private String name;
	private String name2;
	@Size(min = 5, max = 40)
	private String address;
	@Size(min = 3, max = 10)
	private String postcode;
	@Size(min = 3, max = 40)
	private String city;
	private String country;

	public Address(){
		name = "John Doe";
		name2 = "";
		address = "Main Street 15 C 4";
		postcode = "00700";
		city = "Farawayland";
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
