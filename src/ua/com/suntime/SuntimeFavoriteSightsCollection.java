package ua.com.suntime;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.SharedPreferences;

public class SuntimeFavoriteSightsCollection extends SuntimeSightsCollection {

    Context context;
    private static final String PREFNAME = "sights";
    
    public SuntimeFavoriteSightsCollection(Context context) throws JSONException {
        super(load(context));
    }


    private static JSONArray load(Context context) throws JSONException {
        SharedPreferences sights = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        
        return new JSONArray(sights.getString(PREFNAME, "[]"));
    }
    
    public void save() throws JSONException {
        SharedPreferences.Editor editor = this.context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE).edit();
        JSONArray json = new JSONArray();
        editor.putString(PREFNAME, toJSON().toString());
        editor.commit();
    }
    
    
}
