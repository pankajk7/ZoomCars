package com.pankaj.zoomcars.activities;

import java.util.ArrayList;

import com.pankaj.zoomcars.R;
import com.pankaj.zoomcars.adapters.BookingBaseAdapter;
import com.pankaj.zoomcars.database.DBHelper;
import com.pankaj.zoomcars.entities.Booking;
import com.pankaj.zoomcars.utils.AlertMessage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

public class BookingHistory extends AppCompatActivity {

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setTitle("Booking History");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.booking_history_layout);
		findViews();
		getBookings();
	}

	private void findViews() {
		listView = (ListView) findViewById(R.id.listview_booking);
	}

	private void getBookings() {
		DBHelper dbHelper = new DBHelper(BookingHistory.this);
		ArrayList<Booking> bookingsArrayList = dbHelper.getAllBookings();
		if (bookingsArrayList.size() > 0) {
			setAdapter(bookingsArrayList);
		} else {
			new AlertMessage(BookingHistory.this)
					.showTostMessage("No Bookings found.");
		}
		dbHelper.close();
	}

	private void setAdapter(ArrayList<Booking> bookingsArrayList) {
		BookingBaseAdapter<?> adapter = new BookingBaseAdapter<Booking>(
				BookingHistory.this, bookingsArrayList);
		listView.setAdapter(adapter);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == android.R.id.home){
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
	
}
