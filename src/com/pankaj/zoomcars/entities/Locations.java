package com.pankaj.zoomcars.entities;

import java.io.Serializable;

public class Locations implements Serializable{
		String latitude;
		String longitude;
		
		public String getLatitude() {
			return latitude;
		}
		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}
		public String getLongitude() {
			return longitude;
		}
		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}
	}