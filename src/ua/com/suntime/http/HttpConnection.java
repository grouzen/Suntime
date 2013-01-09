package ua.com.suntime.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class HttpConnection {
	
	protected static final String CHARSET = "utf-8";
	
	protected String url;
	private HttpClient client;
	
	public HttpConnection() {
		client = new DefaultHttpClient();
	}
	
	private HttpResponse execute(HttpRequestBase method) {
		try {
			return client.execute(method);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public HttpResponse get(ArrayList<NameValuePair> params) {
		String urlParametrized = url;
		
		if(params != null) {
			urlParametrized += "?" + URLEncodedUtils.format(params, CHARSET);
		}
		Log.i("HttpConnection", urlParametrized);
		HttpGet method = new HttpGet(urlParametrized);
		
		return execute(method);	
	}
	
	public HttpResponse get() {
		HttpGet method = new HttpGet(url);
		
		return execute(method);
	}
	
	public HttpResponse post(ArrayList<NameValuePair> params) {
		HttpPost method = new HttpPost(url);
		
		if(params != null) {
			try {
				((HttpResponse) method).setEntity(new UrlEncodedFormEntity(params));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return execute(method);
	}
	
}
