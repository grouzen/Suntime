package ua.com.suntime;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class SuntimeSight {
	
	private int id;
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
		title = json.getString("title");
		lat = json.getDouble("lat");
		lng = json.getDouble("lng");
		city = json.getString("city");
		descriptionShort = json.getString("description_s");
		//descriptionFull = json.getString("description_b"); not implemented in json api
		address = json.getString("address");
		rating = json.getInt("rating");
		opinion = json.getInt("opinion");
		photos = normalizeJSONArray(json.getString("photos_name"));
		categories = categoriesFromJSON(json.getString("cat_id"), json.getString("cat_title"));
	}
	
	/*
	 * Just normalize ugly array-like string that json api of Suntime returns.
	 * Now array looks like "foo***bar***baz***" where "***" is a delimiter.
	 */
	private ArrayList<String> normalizeJSONArray(String arr) {
	    String[] elems = arr.split("[\\*]{3}");
	    ArrayList<String> normalized = new ArrayList<String>();
	    
	    if(elems != null) {
            for(String el : elems) {
                if(!el.equals("")) {
                    normalized.add(el);
                }
            }
        }
        
        return normalized;
	}
	
	private ArrayList<SuntimeSightCategory> categoriesFromJSON(String ids, String titles) {
	    ArrayList<SuntimeSightCategory> categories = new ArrayList<SuntimeSightCategory>();
	    ArrayList<String> nids = normalizeJSONArray(ids);
	    ArrayList<String> ntitles = normalizeJSONArray(titles);
	    
	    if(nids != null && ntitles != null && nids.size() == ntitles.size()) {
	        for(int i = 0; i < nids.size(); i++) {
	            categories.add(new SuntimeSightCategory(Integer.parseInt(nids.get(i)), ntitles.get(i)));
	        }
	    }
	    
	    return categories;
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
	
	public String getTitle() {
		return title;
	}
	
	public String getCity() {
		return city;
	}
	
	public ArrayList<String> getPhotos() {
	    return photos;
	}
	
	public int getOpinion() {
	    return opinion;
	}
	
	public ArrayList<SuntimeSightCategory> getCategories() {
	    return categories;
	}
	
	public int getRating() {
	    return rating;
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
