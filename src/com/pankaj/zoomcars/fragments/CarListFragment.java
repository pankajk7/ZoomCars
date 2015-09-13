package com.pankaj.zoomcars.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.pankaj.zoomcars.R;
import com.pankaj.zoomcars.activities.CarInfoActivity;
import com.pankaj.zoomcars.adapters.ListBaseAdapter;
import com.pankaj.zoomcars.entities.Car;
import com.pankaj.zoomcars.entities.CarListInfo;
import com.pankaj.zoomcars.utils.AlertMessage;
import com.pankaj.zoomcars.utils.ConnectionDetector;
import com.pankaj.zoomcars.utils.Constants;
import com.pankaj.zoomcars.utils.ReadWriteJsonFileUtils;
import com.pankaj.zoomcars.webservice.RestWebService;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CarListFragment extends Fragment {
	ListView listView;
	TextView apiHitsTextView;
	TextView totalParcelTextView;
	Button priceButton;
	Button ratingButton;
	Button listAllButton;
	EditText searchEditText;

	List<Car> carInfoList;
	ListBaseAdapter<?> adapter;

	List<Car> tempList = new ArrayList<Car>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		init();
		View rootView = inflater.inflate(R.layout.list_cars_layout,
				container, false);
		findViews(rootView);
		listeners();
		hideKeypad();
		setDefaultValues();
		getList();
		getApiHits();
		return rootView;
	}

	private void init() {
		carInfoList = new ArrayList<Car>();
	}

	private void findViews(View rootView) {
		listView = (ListView) rootView.findViewById(R.id.listview_listCars);
		apiHitsTextView = (TextView) rootView
				.findViewById(R.id.textView_listCars_apiHit);
		totalParcelTextView = (TextView) rootView
				.findViewById(R.id.textView_listCars_totalCars);
		priceButton = (Button) rootView
				.findViewById(R.id.button_listCars_price);
		ratingButton = (Button) rootView
				.findViewById(R.id.button_listCars_rating);
		searchEditText = (EditText) rootView.findViewById(R.id.editText_search);
		listAllButton = (Button) rootView
				.findViewById(R.id.button_listCars_listAll);
	}

	private void listeners() {
		listAllButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				adapter.addList(carInfoList);
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Car objCar = (Car) parent.getAdapter().getItem(
						position);
				Intent intent = new Intent(getActivity(),
						CarInfoActivity.class);
				intent.putExtra(Constants.PARAMETER_CAR_INFO, objCar);
				getActivity().startActivity(intent);
			}
		});

		priceButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adapter != null) {
					ArrayList<Car> arrayList = (ArrayList<Car>)adapter.getList();
					Collections.sort(arrayList, byValue());
					adapter.addList(arrayList);
				}
			}
		});

		ratingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (adapter != null) {
					ArrayList<Car> arrayList = (ArrayList<Car>)adapter.getList();
					Collections.sort(arrayList, byRating());
					adapter.addList(arrayList);
				}
			}
		});
		
		
		searchEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				tempList = new ArrayList<Car>();
				tempList.clear();
			}

			@Override
			public void afterTextChanged(Editable s) {
				String string = s.toString();
				if (adapter != null) {
					if (s.length() > 1) {
						if (adapter.getList() != null) {
							ArrayList<Car> arrayList = (ArrayList<Car>) adapter
									.getList();
							for (int i = 0; i < arrayList.size(); i++) {
								string = string.toLowerCase();
								String name = arrayList.get(i).getName()
										.toLowerCase();
								String price = arrayList.get(i).getHourly_rate()
										.toLowerCase();
								String weight = arrayList.get(i).getRating()
										.toLowerCase();
								if (name.startsWith(string)) {
									tempList.add(arrayList.get(i));
								} else if (price.contains(string)) {
									tempList.add(arrayList.get(i));
								} else if (weight.contains(string)) {
									tempList.add(arrayList.get(i));
								}
							}
							adapter.addList(tempList);
							totalParcelTextView.setText(String.format(getActivity().getResources()
									.getString(R.string.total_cars), tempList.size()));
						}
					} else {
						adapter.addList(carInfoList);
						totalParcelTextView.setText(String.format(getActivity().getResources()
								.getString(R.string.total_cars), carInfoList.size()));
					}
				}
			}
		});
	}

	
	private Comparator<Car> byValue() {
		return new Comparator<Car>() {
			@Override
			public int compare(Car lhs, Car rhs) {
				String lhsString = lhs.getHourly_rate();
				String rhsString = rhs.getHourly_rate();
				lhsString = lhsString.replace(",", "");
				rhsString = rhsString.replace(",", "");
				Float fLHS = Float.parseFloat(lhsString);
				Float fRHS = Float.parseFloat(rhsString);
				return fRHS.compareTo(fLHS);
			}
		};
	}

	private Comparator<Car> byRating() {
		return new Comparator<Car>() {
			@Override
			public int compare(Car lhs, Car rhs) {
				return rhs.getRating().compareTo(lhs.getRating());
			}
		};
	}
	
	private void getList() {
		boolean showLoading = true;

		// for checking data in file if exist
		String data = new ReadWriteJsonFileUtils(getActivity())
				.readJsonFileData(Constants.PARAMETER_JSON_FILE_CAR_INFO);
		// if there is data then assign to arraylist
		if (data != null) {
			CarListInfo objCarListInfo = new Gson().fromJson(data,
					CarListInfo.class);
			carInfoList = new ArrayList<Car>(
					Arrays.asList(objCarListInfo.getCars()));
			if (carInfoList.size() > 0) {
				setAdapter();
				totalParcelTextView.setText(String.format(getActivity()
						.getResources().getString(R.string.total_cars),
						carInfoList.size()));
			}
			showLoading = false;
		}

		if (!new ConnectionDetector(getActivity()).isConnectedToInternet()) {
			new AlertMessage(getActivity())
					.showTostMessage(Constants.NO_Internet);
			setDefaultValues();
			return;
		}

		callAPIParcelInfo(showLoading);
	}

	private void callAPIParcelInfo(final boolean showLoading) {
		new RestWebService(getActivity()) {
			public void onSuccess(String data) {
				if (showLoading) {
					CarListInfo objCarListInfo = new Gson().fromJson(
							data, CarListInfo.class);
					carInfoList = new ArrayList<Car>(
							Arrays.asList(objCarListInfo.getCars()));
					new ReadWriteJsonFileUtils(getActivity())
							.createJsonFileData(
									Constants.PARAMETER_JSON_FILE_CAR_INFO,
									data);
					setAdapter();
					totalParcelTextView.setText(String.format(getActivity()
							.getResources().getString(R.string.total_cars),
							carInfoList.size()));
				} else {
					new ReadWriteJsonFileUtils(getActivity())
							.createJsonFileData(
									Constants.PARAMETER_JSON_FILE_CAR_INFO,
									data);
				}
			};

			public void onError(VolleyError error) {
				Log.d("Error", error.toString());
			};
		}.serviceCall(Constants.API_GET_CAR_LIST, "", showLoading);
	}

	private void getApiHits() {
		if (!new ConnectionDetector(getActivity()).isConnectedToInternet()) {
			return;
		}

		new RestWebService(getActivity()) {
			public void onSuccess(String data) {
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(data);
					String apiHits = jsonObject
							.getString(Constants.PARAMETER_API_HITS);
					apiHitsTextView.setText(String.format(getActivity()
							.getResources().getString(R.string.api_hit),
							apiHits));
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};

			public void onError(VolleyError error) {
				Log.d("Error", error.toString());
			};
		}.serviceCall(Constants.API_GET_HIT_COUNT, "", false); // false for not
																// showing
																// loading
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setAdapter() {
		adapter = new ListBaseAdapter(getActivity(), carInfoList) {
			@Override
			public void setValues(ViewHolder holder, Object object) {
				super.setValues(holder, object);
				if (object instanceof Car) {
					Car obj = (Car) object;
					holder.nameTextView.setText(obj.getName() + ", "
							+ obj.getRating());

					Resources res = getResources();
					String inapp = String.format(res.getString(R.string.rupee),
							obj.getHourly_rate());

					holder.priceTextView.setText(inapp);

				}
			}
		};
		listView.setAdapter(adapter);
	}

	private void hideKeypad() {
		View view = getActivity().getCurrentFocus();

		InputMethodManager inputManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (view instanceof EditText) {
			inputManager.hideSoftInputFromWindow(getActivity()
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	private void setDefaultValues() {
		apiHitsTextView.setText(String.format(getActivity().getResources()
				.getString(R.string.api_hit), "N/A"));
		totalParcelTextView.setText(String.format(getActivity().getResources()
				.getString(R.string.total_cars), "N/A"));
	}
}

