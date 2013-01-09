package ua.com.suntime;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;

import android.util.Log;

public class SuntimeSight {
	
	private int id;
	private GeoPoint coords;
	private String title;
	private String city;
	private String descriptionShort;
	private String descriptionFull;
	private String address;
	private int rating;
	private int opinion;
	private ArrayList<String> photos;
	private ArrayList<SuntimeSightCategory> categories;
	
	public SuntimeSight(JSONObject json) throws JSONException {
		id = json.getInt("id");
		coords = new GeoPoint((int) (json.getDouble("lat") * 1E6), (int) (json.getDouble("lng") * 1E6));
		city = json.getString("city");
		descriptionShort = json.getString("description_s");
		//descriptionFull = json.getString("description_b"); Temporarily unavailable
		address = json.getString("address");
		rating = json.getInt("rating");
		opinion = json.getInt("opinion");
		
		// TODO: create photos, categories
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "{ id: " + ((Integer) id).toString() +
				", lat: " + ((Integer) coords.getLatitudeE6()).toString() +
				", lng: " + ((Integer) coords.getLongitudeE6()).toString() +
				", rating: " + ((Integer) rating).toString() +
				" }";
	}
	
}
