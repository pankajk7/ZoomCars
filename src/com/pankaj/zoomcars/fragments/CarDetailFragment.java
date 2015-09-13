package com.pankaj.zoomcars.fragments;

import com.android.volley.toolbox.NetworkImageView;
import com.pankaj.zoomcars.R;
import com.pankaj.zoomcars.ZoomCarsApplication;
import com.pankaj.zoomcars.activities.CalendarActivity;
import com.pankaj.zoomcars.activities.CarInfoActivity;
import com.pankaj.zoomcars.activities.CarInfoActivity.SelectDate;
import com.pankaj.zoomcars.database.DBHelper;
import com.pankaj.zoomcars.entities.Booking;
import com.pankaj.zoomcars.entities.Car;
import com.pankaj.zoomcars.utils.AlertMessage;
import com.pankaj.zoomcars.utils.Constants;
import com.pankaj.zoomcars.utils.DateTimeUtility;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class CarDetailFragment implements SelectDate {

	NetworkImageView imageView;

	TextView ratingTextView;
	TextView nameTextView;
	TextView typeTextView;
	TextView weightTextView;
	TextView priceTextView;
	TextView acTextView;
	TextView qntyTextView;
	TextView phoneTextView;
	TextView colorTextView;
	TextView seaterTextView;
	TextView linkTextView;
	TextView dateTextView;

	Button bookButton;

	RatingBar ratingBar;

	ImageView colorView;
	Activity activity;

	Car objCar;
	String dayString;

	public View getFragmentView(View rootView, Car objCar, Activity activity) {
		this.objCar = objCar;
		this.activity = activity;
		init();
		findView(rootView);
		setViewsValues();
		listeners();
		return rootView;
	}

	private void init() {
		dayString = "";
		((CarInfoActivity) activity).onDateClicked(this);
	}

	private void findView(View rootView) {
		imageView = (NetworkImageView) rootView
				.findViewById(R.id.imageView_parcelInfo);
		nameTextView = (TextView) rootView
				.findViewById(R.id.textview_carInfo_name);
		typeTextView = (TextView) rootView
				.findViewById(R.id.textView_carInfo_type);
		priceTextView = (TextView) rootView
				.findViewById(R.id.textView_carInfo_value);
		seaterTextView = (TextView) rootView
				.findViewById(R.id.textView_carInfo_seater);
		ratingTextView = (TextView) rootView
				.findViewById(R.id.textView_carInfo_rating);
		acTextView = (TextView) rootView.findViewById(R.id.textView_carInfo_ac);
		dateTextView = (TextView) rootView
				.findViewById(R.id.textView_carInfo_date);
		ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar_carInfo);
		bookButton = (Button) rootView.findViewById(R.id.button_carInfo_book);
	}

	private void setViewsValues() {
		try {
			imageView.setImageUrl(objCar.getImage(), ZoomCarsApplication
					.getInstance().getImageLoader());
			nameTextView.setText(objCar.getName());
			typeTextView.setText(String.format(activity.getResources()
					.getString(R.string.type), objCar.getType()));
			ratingTextView.setText(String.format(activity.getResources()
					.getString(R.string.rating_info), objCar.getRating()));
			priceTextView.setText(String.format(activity.getResources()
					.getString(R.string.price_info), objCar.getHourly_rate()));
			seaterTextView.setText(String.format(activity.getResources()
					.getString(R.string.seater_info), objCar.getSeater()));
			if (objCar.getAc().equalsIgnoreCase("1")) {
				acTextView.setText(String.format(activity.getResources()
						.getString(R.string.ac_info), "Yes"));
			} else {
				acTextView.setText(String.format(activity.getResources()
						.getString(R.string.ac_info), "No"));
			}
			ratingBar.setRating(Float.parseFloat(objCar.getRating()));

			dayString = new DateTimeUtility().getDate();
			String dateString = new DateTimeUtility()
					.getSearchDateString(dayString);
			dateTextView.setText(dateString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void listeners() {
		dateTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity, CalendarActivity.class);
				intent.putExtra(Constants.CALENDAR_DATE, dayString);
				activity.startActivityForResult(intent,
						Constants.INTENT_REQUEST_CODE);
			}
		});

		bookButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DBHelper db = new DBHelper(activity);
				Booking obj = new Booking();
				obj.setDate(dayString);
				obj.setName(objCar.getName());
				db.addBooking(obj);
				db.close();
				new AlertMessage(activity).showTostMessage("Added a booking. \n You can check your bookings history in Car Location tab.");
				activity.finish();
			}
		});

	}

	@Override
	public void onSelectDate(String selectedDate) {
		dateTextView.setText(selectedDate);
		dayString = new DateTimeUtility().getyyyymmdd(selectedDate);
	}
}
