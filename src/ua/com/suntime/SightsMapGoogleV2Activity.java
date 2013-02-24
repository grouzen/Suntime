package ua.com.suntime;

import java.util.HashMap;

import org.apache.http.message.BasicNameValuePair;

import ua.com.suntime.http.PhotoLoader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class SightsMapGoogleV2Activity extends SherlockFragmentActivity {

	private GoogleMap gMap;
	private SuntimeSightsCollection sights;
	private HashMap<String, SuntimeSight> markers = new HashMap<String, SuntimeSight>();
	
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
			        
			        // Set custom info window
			        gMap.setInfoWindowAdapter(new SuntimeInfoWindowAdapter(this.markers));
			        
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
    				Marker marker = gMap.addMarker(new MarkerOptions().position(new LatLng(s.getLat(), s.getLng())).title(s.getDescriptionShort()));
    				markers.put(marker.getId(), s);
    			}
    		}
    	}
    	
    }
	
	private class SuntimeInfoWindowAdapter implements InfoWindowAdapter {
        
        private View view;
        private ImageView photoView;
        private TextView titleView;
        private TextView cityView;
        private TextView opinionView;
        private TextView descrShortView;
        private TextView ratingView;
        
        private SuntimePhotosCache photosCache;
        
        public SuntimeInfoWindowAdapter(HashMap<String, SuntimeSight> markers) {
            this.view = getLayoutInflater().inflate(
                    R.layout.adapter_suntime_info_window, null);
            
            this.photoView = (ImageView) view.findViewById(R.id.photo);
            this.titleView = (TextView) view.findViewById(R.id.title);
            this.cityView = (TextView) view.findViewById(R.id.city);
            this.opinionView = (TextView) view.findViewById(R.id.opinion);
            this.descrShortView = (TextView) view.findViewById(R.id.description_s);
            this.ratingView = (TextView) view.findViewById(R.id.rating);
        
            this.photosCache = new SuntimePhotosCache();
        }
        
        @Override
        public View getInfoContents(Marker marker) {
            SuntimeSight sight = markers.get(marker.getId());
            String cacheKey = SuntimePhotosCache.getKey(sight.getId(), sight
                    .getPhotos().get(0));
        
            if (!sight.getPhotos().isEmpty()) {
                if (photosCache.getPhotoFromCache(cacheKey) == null) {
                    photoView.setImageResource(R.drawable.ic_launcher); // TODO: dumb picture
                    
                    new SuntimeInfoWindowAdapterWorker().execute(new MapPhotoLoader(
                            sight.getId(), sight.getPhotos().get(0), photoView,
                            PhotoLoader.SIZE_THUMB, marker));
                } else {
                    photoView.setImageBitmap(photosCache.get(cacheKey));
                }
            }
            
            titleView.setText(sight.getTitle());
            cityView.setText(sight.getCity());
            opinionView.setText(String.valueOf(sight.getOpinion()) + " отзывов");
            descrShortView.setText(sight.getDescriptionShort());
            ratingView.setText(String.valueOf(sight.getRating()));
        
            return view;
        }
        
        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }
        
        private class SuntimeInfoWindowAdapterWorker extends SuntimePhotosWorker {
        
            @Override
            protected void onPostExecute(PhotoLoader loader) {
                Marker marker = ((MapPhotoLoader) loader).getMarker();
                
                loader.applyPhoto();
                photosCache
                        .addPhotoToCache(SuntimePhotosCache.getKey(loader.sightId,
                                loader.photoName), loader.bitmap);
                
                marker.hideInfoWindow();
                marker.showInfoWindow();
                
            }
        
        }
        
        private class MapPhotoLoader extends PhotoLoader {
        
            private final Marker marker;
        
            public MapPhotoLoader(int sightId, String photoName,
                    ImageView photoView, String size, Marker marker) {
                super(sightId, photoName, photoView, size);
                this.marker = marker;
            }
            
            public Marker getMarker() {
                return marker;
            }
        }

	}
	
}
