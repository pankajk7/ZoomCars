package com.pankaj.zoomcars.adapters;

import java.util.ArrayList;
import java.util.List;

import com.pankaj.zoomcars.R;
import com.pankaj.zoomcars.adapters.ListBaseAdapter.ViewHolder;
import com.pankaj.zoomcars.entities.Booking;
import com.pankaj.zoomcars.utils.DateTimeUtility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class BookingBaseAdapter<T> extends BaseAdapter{

	Context context;
	List<?> arrayList;
	LayoutInflater inflater;

	public BookingBaseAdapter(Context context, List<?> arrayList) {
		this.context = context;
		this.arrayList = arrayList;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void addList(List<?> arrayList){
		this.arrayList = new ArrayList<T>();
		this.arrayList = arrayList;
		notifyDataSetChanged();
	}
	
	public List<?> getList(){
		return arrayList;
	}
	
	@Override
	public int getCount() {
		if (arrayList != null) {
			return arrayList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.booking_item_layout, parent,
					false);
			holder = new ViewHolder();
			holder.nameTextView = (TextView) convertView
					.findViewById(R.id.textview_booking_name);
			holder.dateTextView = (TextView) convertView
					.findViewById(R.id.textview_booking_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Booking obj = (Booking)arrayList.get(position);
		holder.nameTextView.setText(obj.getName());
		holder.dateTextView.setText(String.format(context.getResources()
				.getString(R.string.booked_on), new DateTimeUtility().getSearchDateString(obj.getDate())));
		return convertView;
	}

	public void setValues(ViewHolder holder, Object object){
		
	}
	
	public static class ViewHolder {
		public TextView nameTextView;
		public TextView dateTextView;
	}
}
