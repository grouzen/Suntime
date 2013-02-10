package ua.com.suntime.http.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

public class SuntimeApiSights extends SuntimeApi {

	private static final String TAG = "SuntimeApiSights";
	
	public SuntimeApiSights() {
		super();
		
	}
	
	public JSONArray read() throws JSONException {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		addKey(params).add(new BasicNameValuePair("method", "showsight"));
		
		return parse(get(params));
	}
	
	public JSONArray read(ArrayList<NameValuePair> params) throws JSONException {
		addKey(params).add(new BasicNameValuePair("method", "showsight"));
		
		return parse(get(params));
	}
}
