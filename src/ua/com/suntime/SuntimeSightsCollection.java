package ua.com.suntime;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class SuntimeSightsCollection {
	
	private ArrayList<SuntimeSight> sights;
	
	public SuntimeSightsCollection(JSONArray json) {
		sights = new ArrayList<SuntimeSight>();
		
		for(int i = 0; i < json.length(); i++) {
			try {
				sights.add(new SuntimeSight(json.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<SuntimeSight> getSights() {
		return sights;
	}
	
}
