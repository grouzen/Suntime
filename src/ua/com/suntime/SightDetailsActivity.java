package ua.com.suntime;

import java.util.ArrayList;

import ua.com.suntime.http.PhotoLoader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SightDetailsActivity extends Activity {

    private static final String TAG = "SightDetailActivity";
    
    private SuntimeSight sight;
    LinearLayout photosContainer;
    Context context;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sight_details);
        
        Intent intent = getIntent();
        this.sight = (SuntimeSight) intent.getSerializableExtra("ua.com.suntime.SIGHT");
        /*
        ArrayList<SuntimeSightCategory> cats = new ArrayList<SuntimeSightCategory>();
        ArrayList<String> photos = new ArrayList<String>();
        photos.add("33382511511.jpg");
        photos.add("51920258530.jpg");
        cats.add(new SuntimeSightCategory(122, "Горы"));
        
        this.sight = new SuntimeSight(2416, 37.0001, 35.0000, "Тестовый объект",
                "Тестовый город", "Тестовое короткое описание", "Тестовое длинное описание",
                "за маяком", 12, 5, photos, cats);
                
        */
        this.context = this;
        this.photosContainer = (LinearLayout) this.findViewById(R.id.scroller_photos_layout);
        
        if(!sight.getCategories().isEmpty()) {
            StringBuilder text = new StringBuilder();
            ArrayList<SuntimeSightCategory> categories = sight.getCategories();
            
            for(int i = 0; i < categories.size(); i++) {
                text.append(categories.get(i).getTitle());
                
                if(i + 1 < categories.size()) {
                    text.append(", ");
                }
            }
            
            ((TextView) this.findViewById(R.id.categories)).setText(text);
        }
        ((TextView) this.findViewById(R.id.title)).setText(sight.getTitle());
        ((TextView) this.findViewById(R.id.description_s)).setText(sight.getDescriptionShort());
        ((TextView) this.findViewById(R.id.address)).setText(sight.getAddress());
        ((TextView) this.findViewById(R.id.description_b)).setText(sight.getDescriptionFull());
        
        if(sight.getPhotos().size() > 0) {
            for(String photo : sight.getPhotos()) {
               new SuntimeSightDetailsWorker().execute(new PhotoLoader(sight.getId(), photo, PhotoLoader.SIZE_BIG));
            }
        }
    }
     
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_sight_details, menu);
        return true;
    }
    
    private class SuntimeSightDetailsWorker extends 
        SuntimePhotosWorker {
        
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView image = new ImageView(context);
            image.setImageBitmap(bitmap);
            image.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            photosContainer.addView(image);
        }
    } 
    
}
