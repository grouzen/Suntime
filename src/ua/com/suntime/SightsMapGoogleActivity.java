package ua.com.suntime;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import ua.com.suntime.http.api.SuntimeApiSights;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class SightsMapGoogleActivity extends MapActivity {

	private static final String TAG = "SightsMapActivity";
	
	private SuntimeSightsCollection sights;
	private MapView mapView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sights_map);
        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        new GetSuntimeSightsTask().execute(new BasicNameValuePair("order", "id_desc")); 
        
    }
    
    public class GetSuntimeSightsTask extends AsyncTask<NameValuePair, Void, SuntimeSightsCollection> {
    	
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
    			}
    		}
    	}
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sights_map, menu);
        return true;
    }
    
    @Override
    protected boolean isRouteDisplayed() {
    	return false;
    }
}
