package ua.com.suntime;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import ua.com.suntime.http.api.SuntimeApiSights;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class SightsMapGoogleV2Activity extends FragmentActivity {

	private GoogleMap gMap;
	private SuntimeSightsCollection sights;
	
	private final String TAG = "SightsMapGoogleV2Activity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sights_map_google_v2);
		setupMapIfNeeded();
	}

	protected void onResume() {
		super.onResume();
		setupMapIfNeeded();
	}
	
	private void setupMapIfNeeded() {
		if(gMap == null) {
			gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			
			if(gMap != null) {
				Log.i(TAG, "Map setup success!");
				new GetSuntimeSightsTask().execute(
		        		new BasicNameValuePair("order", "rating_desc"),
		        		new BasicNameValuePair("limit", "0,10"));
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sights_map_google_v2, menu);
		return true;
	}

	private class GetSuntimeSightsTask extends AsyncTask<NameValuePair, Void, SuntimeSightsCollection> {
    	
    	@Override
    	protected SuntimeSightsCollection doInBackground(NameValuePair... params) {

            try {
            	ArrayList<NameValuePair> readParams = new ArrayList<NameValuePair>();
            	
            	for(NameValuePair p : params) {
            		readParams.add(p);
            	}
            	
    			sights = new SuntimeSightsCollection(((SuntimeApiSights) new SuntimeApiSights()).read(readParams));
    				
    			return sights;
    		} catch (JSONException e) {
    			e.printStackTrace();
    			return null;
    		}	
    	}
    	
    	@Override
    	protected void onPostExecute(SuntimeSightsCollection sights) {
    		if(sights != null) {
    			
    			for(SuntimeSight s : sights.getSights()) {
    				Log.i(TAG, s.toString());
    				gMap.addMarker(new MarkerOptions().position(new LatLng(s.getLat(), s.getLng())).title(s.getDescriptionShort()));
    			}
    		}
    	}
    	
    }
}
