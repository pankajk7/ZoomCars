package com.pankaj.zoomcars.adapters;

import java.util.ArrayList;
import java.util.List;

import com.pankaj.zoomcars.R;

import android.graphics.PorterDuff;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class ListBaseAdapter<T> extends BaseAdapter {

	Context context;
	List<?> arrayList;
	LayoutInflater inflater;

	public ListBaseAdapter(Context context, List<?> arrayList) {
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
			convertView = inflater.inflate(R.layout.list_item_layout, parent,
					false);
			holder = new ViewHolder();
			holder.nameTextView = (TextView) convertView
					.findViewById(R.id.textview_name);
			holder.priceTextView = (TextView) convertView
					.findViewById(R.id.textview_price);
			holder.ratingBar = (RatingBar) convertView
					.findViewById(R.id.ratingBar);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		setValues(holder, arrayList.get(position));
		
		return convertView;
	}

	public void setValues(ViewHolder holder, Object object){
		
	}
	
	public static class ViewHolder {
		public TextView nameTextView;
		public TextView priceTextView;
		public RatingBar ratingBar;
	}

}
