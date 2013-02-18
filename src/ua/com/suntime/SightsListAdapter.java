package ua.com.suntime;

import java.util.ArrayList;

import ua.com.suntime.http.PhotoLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SightsListAdapter extends ArrayAdapter<SuntimeSight> {

	protected final Context context;
	protected ArrayList<SuntimeSight> sights;
	protected final int rowViewResourceId;
	
	private static final String TAG = "SightsListAdapter"; 
	
	protected ImageView photoView;
	
	public SightsListAdapter(Context context, int rowViewResourceId, ArrayList<SuntimeSight> sights) {
		super(context, rowViewResourceId, sights);
		
		this.context = context;
		this.sights = sights;
		this.rowViewResourceId = rowViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    SuntimeSight sight = sights.get(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(rowViewResourceId, parent, false);
		
		Log.i(TAG, "getView() " + position);
		
		TextView descrShortView = (TextView) view.findViewById(R.id.description_s);
		TextView cityView = (TextView) view.findViewById(R.id.city);
		TextView titleView = (TextView) view.findViewById(R.id.title);
		TextView orderView = (TextView) view.findViewById(R.id.order);
		TextView ratingView = (TextView) view.findViewById(R.id.rating);
		TextView opinionView = (TextView) view.findViewById(R.id.opinion);
		TextView categoriesView = (TextView) view.findViewById(R.id.categories);
		        
		photoView = (ImageView) view.findViewById(R.id.photo);
		
		if(!sight.getPhotos().isEmpty()) {
		    new SuntimeSightsListAdapterWorker().
		            execute(new PhotoLoader(sight.getId(), sight.getPhotos().get(0), PhotoLoader.SIZE_SMALL));
		}
		
		descrShortView.setText(sight.getDescriptionShort());
		cityView.setText(sight.getCity());
		titleView.setText(sight.getTitle());
		orderView.setText(String.valueOf(position + 1));
		ratingView.setText(String.valueOf(sight.getRating()));
		opinionView.setText(String.valueOf(sight.getOpinion()) + " отзывов");
		categoriesView.setText(SuntimeUtils.categoriesToString(sight.getCategories()));
		
		return view;
	}
	
	private class SuntimeSightsListAdapterWorker extends 
	                    SuntimePhotosWorker {
	       
	    @Override
        protected void onPostExecute(Bitmap bitmap) {
	        photoView.setImageBitmap(bitmap);
        } 
	}

}
