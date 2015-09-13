package com.pankaj.zoomcars.entities;

import java.io.Serializable;

public class CarListInfo implements Serializable{
	Car[] cars;

	public Car[] getCars() {
		return cars;
	}

	public void setCars(Car[] cars) {
		this.cars = cars;
	}
}
