package com.pankaj.zoomcars.entities;

import java.io.Serializable;

public class Car implements Serializable{
	String name;
	String image;
	String type;
	String hourly_rate;
	String rating;
	String seater;
	String ac;
	Locations location;
	
	@Override
	public String toString() {
		return "Name: "+ name + "\n Type: " + type + "\n Price/hr: " + hourly_rate +
				"\n Seater: "+ seater + "\n Rating: " + rating +
				"\n Latitude: " + location.getLatitude()
				 + "\n Longitude: " + location.getLongitude();
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHourly_rate() {
		return hourly_rate;
	}
	public void setHourly_rate(String hourly_rate) {
		this.hourly_rate = hourly_rate;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getSeater() {
		return seater;
	}
	public void setSeater(String seater) {
		this.seater = seater;
	}
	public String getAc() {
		return ac;
	}
	public void setAc(String ac) {
		this.ac = ac;
	}
	public Locations getLocation() {
		return location;
	}
	public void setLocation(Locations location) {
		this.location = location;
	}
}
