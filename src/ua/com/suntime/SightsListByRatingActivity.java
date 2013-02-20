package ua.com.suntime;

import org.apache.http.message.BasicNameValuePair;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

public class SightsListByRatingActivity extends SherlockListActivity {
    	
    private SuntimeSightsCollection sights = null;
    private final String TAG = "SightsListByRatingActivity";
    private Context context;

    private SuntimePhotosCache photosCache;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sights_list_by_rating);

        this.context = this;
        
        this.photosCache = new SuntimePhotosCache();
        
        if(this.sights == null) {    
            new SuntimeSightsListByRatingWorker().execute(new BasicNameValuePair("order", "rating_desc"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_sights_list_by_rating, menu);
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

    private class SuntimeSightsListByRatingWorker extends SuntimeSightsWorker {
        @Override
        protected void onPostExecute(SuntimeSightsCollection response) {
            setSights(response);
            		
            if(response != null) {
                SightsListByRatingAdapter adapter = new SightsListByRatingAdapter(context, response.getSights(), photosCache);
                setListAdapter(adapter);
            			
            }
        }
    }
    
}
