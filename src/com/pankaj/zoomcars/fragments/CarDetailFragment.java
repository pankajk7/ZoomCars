package com.pankaj.zoomcars.fragments;


import com.android.volley.toolbox.NetworkImageView;
import com.pankaj.zoomcars.R;
import com.pankaj.zoomcars.ZoomCarsApplication;
import com.pankaj.zoomcars.entities.Car;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CarDetailFragment {

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

	ImageView colorView;
	Activity activity;

	Car objCar;

	public View getFragmentView(View rootView, Car objCar,
			Activity activity) {
		this.objCar = objCar;
		this.activity = activity;
		findView(rootView);
		setViewsValues();
		return rootView;
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
		acTextView = (TextView) rootView
				.findViewById(R.id.textView_carInfo_ac);
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
			if(objCar.getAc().equalsIgnoreCase("1")){
				acTextView.setText(String.format(activity.getResources()
						.getString(R.string.ac_info), "Yes"));
			}else{
				acTextView.setText(String.format(activity.getResources()
						.getString(R.string.ac_info), "No"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
