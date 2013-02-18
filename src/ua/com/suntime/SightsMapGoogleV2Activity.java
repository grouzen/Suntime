package ua.com.suntime;

import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class SightsMapGoogleV2Activity extends SherlockFragmentActivity {

	private GoogleMap gMap;
	private SuntimeSightsCollection sights;
	
	private static final String TAG = "SightsMapGoogleV2Activity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sights_map_google_v2);
		setupMapIfNeeded(savedInstanceState);
	}

	protected void onResume(Bundle savedInstanceState) {
		super.onResume();
		setupMapIfNeeded(savedInstanceState);
	}
	
	private void setupMapIfNeeded(Bundle savedInstanceState) {
		if(gMap == null) {
			gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			
			if(gMap != null) {
				if (savedInstanceState == null) {
			        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(45.15, 34.4));
			        CameraUpdate zoom = CameraUpdateFactory.zoomTo((float) 8.3);

			        gMap.moveCamera(center);
			        gMap.animateCamera(zoom);
				}
				
				new SuntimeMapGoogleV2Worker().execute(
		        		new BasicNameValuePair("order", "rating_desc"),
		        		new BasicNameValuePair("limit", "0,10"));
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_sights_map_google_v2, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    Intent intent;
	    
	    switch(item.getItemId()) {
	    case R.id.menu_rating:
	        intent = new Intent(this, SightsListByRatingActivity.class);
	        startActivity(intent);
	        return true;
	    case R.id.menu_favorites:
	        intent = new Intent(this, SightsListFavoritesActivity.class);
	        startActivity(intent);
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

	public SuntimeSightsCollection getSights() {
		return sights;
	}

	public void setSights(SuntimeSightsCollection sights) {
		this.sights = sights;
	}

	private class SuntimeMapGoogleV2Worker extends SuntimeSightsWorker {
    	
    	@Override
    	protected void onPostExecute(SuntimeSightsCollection response) {
    	    setSights(response);
    		
    		if(response != null) {
    			
    			for(SuntimeSight s : response.getSights()) {
    				gMap.addMarker(new MarkerOptions().position(new LatLng(s.getLat(), s.getLng())).title(s.getDescriptionShort()));
    			}
    		}
    	}
    	
    }
}
