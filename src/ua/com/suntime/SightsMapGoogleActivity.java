package ua.com.suntime;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import ua.com.suntime.http.api.SuntimeApiSights;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
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
        
        new GetSuntimeSightsTask().execute(
        		new BasicNameValuePair("order", "rating_desc"),
        		new BasicNameValuePair("limit", "0,10")
        		); 
        
    }
    
	private class MarkerOverlay extends com.google.android.maps.Overlay {
		
		private GeoPoint geoPoint;
		
		public MarkerOverlay(GeoPoint geoPoint) { 
			this.geoPoint = geoPoint;
		}
		
		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);
			/*
			if(!shadow) {
				Point point = new Point();
				mapView.getProjection().toPixels(geoPoint, point);
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker);
				canvas.drawBitmap(bitmap, point.x - bitmap.getWidth() / 2, point.y - bitmap.getHeight(), null);
			}
			*/
		}
		
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
    			List<Overlay> overlays = mapView.getOverlays();
    			overlays.clear();
    			
    			for(SuntimeSight s : sights.getSights()) {
    				Log.i(TAG, s.toString());
    				//overlays.add(new MarkerOverlay());
    			}
    			
    			mapView.invalidate();
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
