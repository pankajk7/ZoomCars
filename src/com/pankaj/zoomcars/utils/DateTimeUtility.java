package com.pankaj.zoomcars.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtility {

	public String getDate() {
		try {
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
					Locale.getDefault());
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getSearchDateString(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		try {
			Date date = sdf.parse(dateString);
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MMM/yyyy",
					Locale.getDefault());
			return sdf1.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getyyyymmdd(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy",
				Locale.getDefault());
		try {
			Date date = sdf.parse(dateString);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd",
					Locale.getDefault());
			return sdf1.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
