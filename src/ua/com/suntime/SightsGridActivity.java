package ua.com.suntime;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SightsGridActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sights_grid);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sights_grid, menu);
        return true;
    }
}
