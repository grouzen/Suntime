package ua.com.suntime;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;

import android.util.Log;

public class SuntimeSight {
	
	private int id;
	//private GeoPoint coords;
	private double lat;
	private double lng;
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
		//coords = new GeoPoint((int) (json.getDouble("lat") * 1E6), (int) (json.getDouble("lng") * 1E6));
		lat = json.getDouble("lat");
		lng = json.getDouble("lng");
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
	
	public double getLat() {
		return lat;
	}
	
	public double getLng() {
		return lng;
	}
	
	public String getDescriptionShort() {
		return descriptionShort;
	}
	
	
	@Override
	public String toString() {
		return "{ id: " + ((Integer) id).toString() +
				", lat: " + ((Double) lat).toString() +
				", lng: " + ((Double) lng).toString() +
				", rating: " + ((Integer) rating).toString() +
				" }";
	}
}
