package ua.com.suntime;

import org.apache.http.message.BasicNameValuePair;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.content.Context;

public class SightsListByRatingActivity extends SherlockListActivity {
    	
    private SuntimeSightsCollection sights;
    private final String TAG = "SightsListByRatingActivity";
    private Context context;
    	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sights_list_by_rating);
        
        ListView list = (ListView) findViewById(android.R.id.list);
        RelativeLayout header = (RelativeLayout) this.getLayoutInflater().inflate(R.layout.widget_sights_list_header, null);
        
        this.context = this;
        //list.set
        list.addHeaderView(header);        
        new SuntimeSightsListByRatingWorker().execute(new BasicNameValuePair("order", "rating_desc"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_sights_list_by_rating, menu);
        return true;
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
                SightsListByRatingAdapter adapter = new SightsListByRatingAdapter(context, response.getSights());
                setListAdapter(adapter);
            			
            }
        }
    }
    
}
