package ua.com.suntime;

import java.util.ArrayList;

import ua.com.suntime.http.PhotoLoader;

import android.content.Context;
import android.graphics.Bitmap;
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
		
		TextView descrShortView = (TextView) view.findViewById(R.id.description_s);
		TextView cityView = (TextView) view.findViewById(R.id.city);
		TextView titleView = (TextView) view.findViewById(R.id.title);
		
		photoView = (ImageView) view.findViewById(R.id.photo);
		
		if(!sight.getPhotos().isEmpty()) {
		    new SuntimeSightsListAdapterWorker().execute(new PhotoLoader(sight.getId(), sight.getPhotos().get(0), PhotoLoader.SIZE_THUMB));
		}
		
		descrShortView.setText(sight.getDescriptionShort());
		cityView.setText(sight.getCity());
		titleView.setText(sight.getTitle());
		
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
