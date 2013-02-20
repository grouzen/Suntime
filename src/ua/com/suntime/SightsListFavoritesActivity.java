package ua.com.suntime;

import org.json.JSONException;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.ListView;

public class SightsListFavoritesActivity extends SherlockListActivity {

    private SuntimeFavoriteSightsCollection sights;
    private Context context;

    private SuntimePhotosCache photosCache;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sights_list_favorites);
        
        this.context = this;
        this.photosCache = new SuntimePhotosCache();
        
        try {
            sights = new SuntimeFavoriteSightsCollection(context);
            SightsListFavoritesAdapter adapter = new SightsListFavoritesAdapter(context, sights.getSights(), photosCache);
            setListAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.activity_sights_list_favorites, menu);
        return true;
    }
    
    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        Intent intent = new Intent(this, SightDetailsActivity.class);
        
        intent.putExtra("ua.com.suntime.SIGHT", sights.getSights().get(position));
        startActivity(intent);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        
        switch(item.getItemId()) {
        case R.id.menu_rating:
            intent = new Intent(this, SightsListByRatingActivity.class);
            startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    

}
