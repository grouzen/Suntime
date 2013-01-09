package ua.com.suntime;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SightsListActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sights_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sights_list, menu);
        return true;
    }
}
