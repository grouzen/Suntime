package ua.com.suntime;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class SightsListFavoritesAdapter extends SightsListAdapter {

    public SightsListFavoritesAdapter(Context context,
            ArrayList<SuntimeSight> sights, SuntimePhotosCache photosCache) {
        super(context, R.layout.adapter_sights_list_favorites, sights,
                photosCache);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        return view;
    }

}
