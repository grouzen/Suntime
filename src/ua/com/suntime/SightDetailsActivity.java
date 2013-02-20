package ua.com.suntime;

import java.util.ArrayList;

import org.json.JSONException;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import ua.com.suntime.http.PhotoLoader;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SightDetailsActivity extends SherlockFragmentActivity {

    private static final String TAG = "SightDetailActivity";
    
    private SuntimeSight sight;
    LinearLayout photosContainer;
    Context context;
    GoogleMap gMap;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sight_details);
        
        Intent intent = getIntent();
        this.sight = (SuntimeSight) intent.getSerializableExtra("ua.com.suntime.SIGHT");
        
        this.context = this;
        this.photosContainer = (LinearLayout) this.findViewById(R.id.scroller_photos_layout);
        
        Button button_favorites = (Button) this.findViewById(R.id.button_favotites);
        button_favorites.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    SuntimeFavoriteSightsCollection f = new SuntimeFavoriteSightsCollection(context);
                    Log.i(TAG, String.valueOf(f.getSights().size()));
                    f.getSights().add(sight);
                    f.save();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        
        ((TextView) this.findViewById(R.id.categories)).setText(SuntimeUtils.categoriesToString(sight.getCategories()));
        ((TextView) this.findViewById(R.id.title)).setText(sight.getTitle());
        ((TextView) this.findViewById(R.id.description_s)).setText(sight.getDescriptionShort());
        ((TextView) this.findViewById(R.id.address)).setText(sight.getAddress());
        ((TextView) this.findViewById(R.id.description_b)).setText(sight.getDescriptionFull());
        
        if(sight.getPhotos().size() > 0) {
            for(String photo : sight.getPhotos()) {
                ImageView photoView = new ImageView(context);
                photoView.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
                photosContainer.addView(photoView);
                
                new SuntimeSightDetailsWorker().execute(new PhotoLoader(sight.getId(), photo, photoView, PhotoLoader.SIZE_BIG));
            }
        }
        
        setupMapIfNeeded(savedInstanceState);
    }
     
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.activity_sight_details, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    private class SuntimeSightDetailsWorker extends 
                                    SuntimePhotosWorker {
        
        @Override
        protected void onPostExecute(PhotoLoader loader) {
            loader.applyPhoto();
        }
    }
    
    
    private void setupMapIfNeeded(Bundle savedInstanceState) {
        if(gMap == null) {
            gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment)).getMap();
            
            if(gMap != null) {
                if (savedInstanceState == null) {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(45.15, 34.4));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo((float) 8.3);

                    gMap.moveCamera(center);
                    gMap.animateCamera(zoom);
                }
                
            }
        }
    }
    
    
}
