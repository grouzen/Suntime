package ua.com.suntime.http.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import ua.com.suntime.http.HttpConnection;

public abstract class SuntimeApi extends HttpConnection {
	
	private static final String URL = "http://suntime.com.ua/api/";
	private static final String KEY = "0d489962300712434ffc31852bd21960";
	
	public SuntimeApi() {
		this.url = URL;
	}
	
	protected JSONArray parse(HttpResponse response) throws JSONException {
		if(response != null) {
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				try {
					BufferedReader bf = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					StringBuilder json = new StringBuilder();
					String bfLine = "";
					
					while((bfLine = bf.readLine()) != null) {
						json.append(bfLine).append('\n');
					}
					
					return new JSONArray(json.toString());
				} catch (IllegalStateException e) {
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
				
			}
		}
		
		return null;
	}
	
	protected ArrayList<NameValuePair> addKey(ArrayList<NameValuePair> params) {
		params.add(new BasicNameValuePair("key", KEY));
		return params; // For chaining calls
	}
	
	
}
