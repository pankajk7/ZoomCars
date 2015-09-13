package com.pankaj.zoomcars.utils;

public class Constants {

	
	// Base Url
	public final static String BASE_URL = "http://zoomcar.0x10.info/api/";
	public final static String SUFFIX_URL = "";
	public final static String URL_TAIL = ".json";
	
	// Fonts
	public final static String FONT_CIRCULAR = "fonts/Circular_Air-Book.ttf";


	
	//Common
	public final static int TIMEOUT = 25000;
	public final static String NO_Internet = "No Internet found. Please enable mobile data or wifi";
	public final static String SHARED_PREF_FILE_NAME = "porter_file";
	public final static String ERROR_MESSAGE = "error_message";
	public final static String PARAMETER_BUNDLE = "bundle";
	public final static int INTENT_REQUEST_CODE = 1;
	
	
	//Calendar
	public final static String CALENDAR_DATE = "selected_date";
	
	//step2
	public final static String FRAGMENT_TAG_1 = "fragment1";
	public final static String FRAGMENT_TAG_2 = "fragment2";
	
	//Parameters
	public final static String PARAMETER_JSON_FILE_CAR_INFO = "car_info";
	public final static String PARAMETER_API_HITS = "api_hits";
	public final static String PARAMETER_CAR_INFO = "car_info";
	
	//Messages
	public final static String SUCCESS = "Success";
	public final static String ERROR = "Error";
	
	public final static String API_GET_CAR_LIST = "zoomcar?type=json&query=list_cars";
	public final static String API_GET_HIT_COUNT = "zoomcar?type=json&query=api_hits";
}
