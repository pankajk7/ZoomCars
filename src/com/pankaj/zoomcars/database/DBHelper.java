package com.pankaj.zoomcars.database;

import java.util.ArrayList;
import java.util.HashMap;

import com.pankaj.zoomcars.entities.Booking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "zoomcars_demo.db";
	public static final String TABLE_NAME = "car";
	public static final String CAR_ID = "id";
	public static final String CAR_NAME = "name";
	public static final String DATE = "date";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + TABLE_NAME + " (" + CAR_ID
				+ " integer primary key, " + CAR_NAME + " text, " + DATE + " text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	public boolean addBooking(Booking obj) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(CAR_ID, obj.getId());
		contentValues.put(CAR_NAME, obj.getName());
		contentValues.put(DATE, obj.getDate());
		db.insert(TABLE_NAME, null, contentValues);
		return true;
	}

	public boolean addBookings(Booking[] array) {
		SQLiteDatabase db = this.getWritableDatabase();
		for (int i = 0; i < array.length; i++) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(CAR_ID, array[i].getId());
			contentValues.put(CAR_NAME, array[i].getName());
			db.insert(TABLE_NAME, null, contentValues);
		}
		return true;
	}

	public String getLargestCarId() {
		SQLiteDatabase db = this.getReadableDatabase();
		// String query = "select " + AREAS_ID + " from "
		// + AREA_TABLE_NAME + " where " + AREAS_ID + "=(select max("
		// + AREAS_ID + ") from " + AREA_TABLE_NAME + ")";
		String query = "SELECT * from car ORDER BY id DESC LIMIT 1";
		Cursor res = db.rawQuery(query, null);
		if (res != null) {
			if (res.getCount() > 0) {
				res.moveToFirst();
				// return res.getString(res.getColumnIndex(AREAS_ID));
				return res.getString(0);
			}
			return "0";
		}
		return null;
	}

	public Cursor getData(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + TABLE_NAME
				+ " where id=" + id + "", null);
		return res;
	}

	public Cursor getData(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + TABLE_NAME
				+ " where id=" + id, null);
		return res;
	}

	public int numberOfRows() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
		return numRows;
	}


	public ArrayList<Booking> getAllBookings() {
		ArrayList<Booking> arraylist = new ArrayList<Booking>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
		res.moveToFirst();

		while (res.isAfterLast() == false) {
			Booking obj = new Booking();
			obj.setId(res.getString(res.getColumnIndex(CAR_ID)));
			obj.setName(res.getString(res.getColumnIndex(CAR_NAME)));
			obj.setDate(res.getString(res.getColumnIndex(DATE)));
			arraylist.add(obj);
			res.moveToNext();
		}
		return arraylist;
	}
	
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}
}