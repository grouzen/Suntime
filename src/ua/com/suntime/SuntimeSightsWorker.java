package ua.com.suntime;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.json.JSONException;

import ua.com.suntime.SuntimeSightsCollection;
import ua.com.suntime.http.api.SuntimeApiSights;
import android.os.AsyncTask;


public class SuntimeSightsWorker extends AsyncTask<NameValuePair, Void, SuntimeSightsCollection>  {
	
	@Override
	protected SuntimeSightsCollection doInBackground(NameValuePair... params) {

        try {
        	ArrayList<NameValuePair> readParams = new ArrayList<NameValuePair>();
        	
        	for(NameValuePair p : params) {
        		readParams.add(p);
        	}
        	
			SuntimeSightsCollection sights = new SuntimeSightsCollection(((SuntimeApiSights) new SuntimeApiSights()).read(readParams));
				
			return sights;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}	
	}
}
