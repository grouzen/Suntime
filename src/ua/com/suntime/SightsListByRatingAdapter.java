package ua.com.suntime;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SightsListByRatingAdapter extends SightsListAdapter {

	public SightsListByRatingAdapter(Context context, ArrayList<SuntimeSight> sights, SuntimePhotosCache photos) {
		super(context, R.layout.adapter_sights_list_by_rating , sights, photos);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
	    View view = super.getView(position, convertView, parent);
		
		return view;
	}

}
