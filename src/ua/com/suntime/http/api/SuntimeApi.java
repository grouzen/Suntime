package ua.com.suntime.http.api;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import ua.com.suntime.http.HttpConnection;

public abstract class SuntimeApi extends HttpConnection {
	
	private static final String URL = "http://suntime.com.ua/api/";
	private static final String KEY = "0d489962300712434ffc31852bd21960";
	
	public SuntimeApi() {
		this.url = URL;
	}
	
	protected ArrayList<NameValuePair> addKey(ArrayList<NameValuePair> params) {
		params.add(new BasicNameValuePair("key", KEY));
		return params; // For chaining calls
	}
	
	
}
