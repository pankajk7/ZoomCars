package com.pankaj.zoomcars.activities;

import java.util.GregorianCalendar;

import com.pankaj.zoomcars.R;
import com.pankaj.zoomcars.adapters.SearchCalendarAdapter;
import com.pankaj.zoomcars.utils.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CalendarActivity extends Activity {

	TextView monthNameTextView;

	ImageButton closeButton;

	RelativeLayout next;
	RelativeLayout previous;

	GridView gridview;

	public GregorianCalendar month, itemmonth;// calendar instances.
	public SearchCalendarAdapter adapter;// adapter instance
	public Handler handler;// for grabbing some event values for showing the dot
							// marker.
	String selectedDateString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_layout);

		init();
		findViews();
		setCalendar();
		listeners();
	}

	private void init() {
		selectedDateString = "";
		try {
			selectedDateString = getIntent().getExtras().getString(Constants.CALENDAR_DATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void findViews() {
		monthNameTextView = (TextView) findViewById(R.id.searchCalendar_textview_month);
		next = (RelativeLayout) findViewById(R.id.searchCalendar_next);
		previous = (RelativeLayout) findViewById(R.id.searchCalendar_previous);
		gridview = (GridView) findViewById(R.id.searchCalendar_gridview);
		closeButton = (ImageButton) findViewById(R.id.searchCalendar_imageButton_close);
	}

	public void setCalendar() {
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		itemmonth = (GregorianCalendar) month.clone();

		adapter = new SearchCalendarAdapter(CalendarActivity.this, month);

		gridview.setAdapter(adapter);
		handler = new Handler();
		refreshCalendar();
	}

	private void listeners() {

		closeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				commonCloseMethod();
			}
		});

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();
			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				((SearchCalendarAdapter) parent.getAdapter()).setSelected(v);
				String selectedGridDate = SearchCalendarAdapter.dayString
						.get(position);
				String[] separatedTime = selectedGridDate.split("-");
				String gridvalueString = separatedTime[2].replaceFirst("^0*",
						"");// taking last part of date. ie; 2 from 2012-12-02.
				int gridvalue = Integer.parseInt(gridvalueString);
				// navigate to next or previous month on clicking offdays.
				// if ((gridvalue > 10) && (position < 8)) {
				// setPreviousMonth();
				// refreshCalendar();
				// } else if ((gridvalue < 7) && (position > 28)) {
				// setNextMonth();
				// refreshCalendar();
				// }
				((SearchCalendarAdapter) parent.getAdapter()).setSelected(v);
				Intent intent = new Intent();
				intent.putExtra(Constants.CALENDAR_DATE,
						SearchCalendarAdapter.dayString.get(position));
				setResult(RESULT_OK, intent);
				finish();// finishing activity
			}

		});

	}

	@Override
	public void onBackPressed() {
		commonCloseMethod();
	}

	private void commonCloseMethod() {
		Intent intent = new Intent();
		setResult(RESULT_CANCELED, intent);
		finish();
	}

	protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1),
					month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) + 1);
		}

	}

	protected void setPreviousMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}

	}

	public void refreshCalendar() {
		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		 handler.post(calendarUpdater); // generate some calendar items

		monthNameTextView.setText(android.text.format.DateFormat.format("MMMM",
				month)
				+ " "
				+ android.text.format.DateFormat.format("yyyy", month));
	}

	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			adapter.setItems(selectedDateString);
			adapter.notifyDataSetChanged();
		}
	};

}
