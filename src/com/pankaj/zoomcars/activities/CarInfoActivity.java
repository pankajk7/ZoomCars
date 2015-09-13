package com.pankaj.zoomcars.activities;


import com.pankaj.zoomcars.R;
import com.pankaj.zoomcars.components.SlidingTabLayout;
import com.pankaj.zoomcars.entities.Car;
import com.pankaj.zoomcars.fragments.CarDetailFragment;
import com.pankaj.zoomcars.fragments.CarLocationInfoFragment;
import com.pankaj.zoomcars.utils.Constants;
import com.pankaj.zoomcars.utils.DateTimeUtility;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class CarInfoActivity extends AppCompatActivity{

	private SlidingTabLayout mSlidingTabLayout;
	private ViewPager mViewPager;
	
	Car objCar;
	String selectedDate;
	View scrolledView;
	
	SelectDate selectDateListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setTitle("Car Details");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.car_info_layout);
		init();
		// BEGIN_INCLUDE (setup_viewpager)
		// Get the ViewPager and set it's PagerAdapter so that it can display
		// items
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mViewPager.setAdapter(new SamplePagerAdapter());
		// END_INCLUDE (setup_viewpager)

		// BEGIN_INCLUDE (setup_slidingtablayout)
		// Give the SlidingTabLayout the ViewPager, this must be done AFTER the
		// ViewPager has had
		// it's PagerAdapter set.
		mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
		mSlidingTabLayout.setCustomTabView(R.layout.tabview_layout,
				R.id.tabview_textview);
		mSlidingTabLayout.setSelectedIndicatorColors(getColorResourceId(R.color.primary_color));
		mSlidingTabLayout.setViewPager(mViewPager);
		// END_INCLUDE (setup_slidingtablayout)
	}
	
	public int getColorResourceId(int id) {
	    final int version = Build.VERSION.SDK_INT;
	    if (version >= 23) {
	        return ContextCompat.getColor(CarInfoActivity.this, id);
	    } else {
	        return getResources().getColor(id);
	    }
	}
	
	
	private void init() {
		selectedDate = "";
		scrolledView = null;
		try {
			Intent intent = getIntent();
			objCar = (Car) intent
					.getSerializableExtra(Constants.PARAMETER_CAR_INFO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * The {@link android.support.v4.view.PagerAdapter} used to display pages in
	 * this sample. The individual pages are simple and just display two lines
	 * of text. The important section of this class is the
	 * {@link #getPageTitle(int)} method which controls what is displayed in the
	 * {@link SlidingTabLayout}.
	 */
	class SamplePagerAdapter extends PagerAdapter {

		/**
		 * @return the number of pages to display
		 */
		@Override
		public int getCount() {
			return 2;
		}

		/**
		 * @return true if the value returned from
		 *         {@link #instantiateItem(ViewGroup, int)} is the same object
		 *         as the {@link View} added to the {@link ViewPager}.
		 */
		@Override
		public boolean isViewFromObject(View view, Object o) {
			return o == view;
		}

		// BEGIN_INCLUDE (pageradapter_getpagetitle)
		/**
		 * Return the title of the item at {@code position}. This is important
		 * as what this method returns is what is displayed in the
		 * {@link SlidingTabLayout}.
		 * <p>
		 * Here we construct one using the position value, but for real
		 * application the title should refer to the item's contents.
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			String title;
			if (position == 0) {
				title = "Car Info";
			} else {
				title = "Car Location";
			}
			return title;
		}

		// END_INCLUDE (pageradapter_getpagetitle)

		/**
		 * Instantiate the {@link View} which should be displayed at
		 * {@code position}. Here we inflate a layout from the apps resources
		 * and then change the text view to signify the position.
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = null;
				if (position == 0) {
					view = getLayoutInflater().inflate(
							R.layout.car_details_layout,
							container, false);
					view = new CarDetailFragment().getFragmentView(view,
							objCar, CarInfoActivity.this);
				} else {
					view = getLayoutInflater().inflate(
							R.layout.car_location_layout, container, false);
					view = new CarLocationInfoFragment().getFragmentView(view,
							objCar, CarInfoActivity.this);
				}
				container.addView(view);
				scrolledView = view;
			return view;
		}

		/**
		 * Destroy the item from the {@link ViewPager}. In our case this is
		 * simply removing the {@link View}.
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == android.R.id.home){
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constants.INTENT_REQUEST_CODE) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
				String dateString = data.getExtras().getString(
						Constants.CALENDAR_DATE);
				selectedDate = new DateTimeUtility().getSearchDateString(dateString);
				
				selectDateListener.onSelectDate(selectedDate);  //callback for date selection
			}
		}
	}
	
	public interface SelectDate{
		public void onSelectDate(String selectedDate);
	}
	
	public void onDateClicked(SelectDate selectDateListeners){
		this.selectDateListener = selectDateListeners;
	}
}
