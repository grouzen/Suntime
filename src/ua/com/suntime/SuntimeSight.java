package ua.com.suntime;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class SuntimeSight implements Serializable {
	
    private static final long serialVersionUID = 4459167065338265436L;
    
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
	
	public SuntimeSight(int id, double lat, double lng, String title, String city,
	        String descriptionShort, String descriptionFull, String address, int rating,
	        int opinion, ArrayList<String> photos, ArrayList<SuntimeSightCategory> categories) {
	    this.id = id;
	    this.lat = lat;
	    this.lng = lng;
	    this.title = title;
	    this.city = city;
	    this.descriptionFull = descriptionFull;
	    this.descriptionShort = descriptionShort;
	    this.address = address;
	    this.rating = rating;
	    this.opinion = opinion;
	    this.photos = photos;
	    this.categories = categories;
	}
	
	public SuntimeSight(JSONObject json) throws JSONException {
		id = json.getInt("id");
		title = json.getString("title");
		lat = json.getDouble("lat");
		lng = json.getDouble("lng");
		city = json.getString("city");
		descriptionShort = json.getString("description_s");
		descriptionFull = json.getString("description_b");
		address = json.getString("address");
		rating = json.getInt("rating");
		opinion = json.getInt("opinion");
		photos = photosFromJSON(json.getString("photos_name"));
		categories = categoriesFromJSON(json.getString("cat_id"), json.getString("cat_title"));
	}
	
	public JSONObject toJSON() throws JSONException {
	    JSONObject json = new JSONObject();
	    
	    json.put("id", id);
	    json.put("title", title);
	    json.put("lat", lat);
	    json.put("lng", lng);
	    json.put("city", city);
	    json.put("description_s", descriptionShort);
	    json.put("description_b", descriptionFull);
	    json.put("address", address);
	    json.put("rating", rating);
	    json.put("opinion", opinion);
	    json.put("photos_name", photosToString());
	    json.put("cat_id", categoriesIdToString());
	    json.put("cat_title", categoriesTitleToString());
	    
        return json;
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
	
	private String photosToString() {
	    StringBuilder string = new StringBuilder();
	    
	    for(String p : photos) {
	        string.append(p + "***");
	    }
	    
	    return string.toString();
	}
	
	private ArrayList<String> photosFromJSON(String photos) {
	    return normalizeJSONArray(photos);
	}
	
	private String categoriesIdToString() {
	    StringBuilder string = new StringBuilder();
	    
	    for(SuntimeSightCategory c : categories) {
	        string.append(c.getId() + "***");
	    }
	    
	    return string.toString();
	}
	
	private String categoriesTitleToString() {
        StringBuilder string = new StringBuilder();
        
        for(SuntimeSightCategory c : categories) {
            string.append(c.getTitle() + "***");
        }
        
        return string.toString();
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

    public String getDescriptionFull() {
        return descriptionFull;
    }

    public String getAddress() {
        return address;
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
