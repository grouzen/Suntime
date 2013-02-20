package ua.com.suntime;

import java.util.ArrayList;

import ua.com.suntime.http.PhotoLoader;

import android.content.Context;
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
	private SuntimePhotosCache photosCache;
	
	private static final String TAG = "SightsListAdapter"; 
	
	/*
	 * ViewHolder pattern for speed optimizations by Android Google Team.
	 */
	static class ViewHolder {
	    TextView descrShortView;
	    TextView cityView;
	    TextView titleView;
	    TextView orderView;
	    TextView ratingView;
	    TextView opinionView;
	    TextView categoriesView;
	    ImageView photoView;
	}
	
	public SightsListAdapter(Context context, int rowViewResourceId, ArrayList<SuntimeSight> sights, SuntimePhotosCache photosCache) {
		super(context, rowViewResourceId, sights);
		
		this.context = context;
		this.sights = sights;
		this.photosCache = photosCache;
		this.rowViewResourceId = rowViewResourceId;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
	    ViewHolder holder;
	    SuntimeSight sight = sights.get(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		String cacheKey = SuntimePhotosCache.getKey(sight.getId(), sight.getPhotos().get(0));
		
		if(view == null) {
		    view = inflater.inflate(rowViewResourceId, parent, false);
		    
		    holder = new ViewHolder();
		    holder.descrShortView = (TextView) view.findViewById(R.id.description_s);
	        holder.cityView = (TextView) view.findViewById(R.id.city);
	        holder.titleView = (TextView) view.findViewById(R.id.title);
	        holder.orderView = (TextView) view.findViewById(R.id.order);
	        holder.ratingView = (TextView) view.findViewById(R.id.rating);
	        holder.opinionView = (TextView) view.findViewById(R.id.opinion);
	        holder.categoriesView = (TextView) view.findViewById(R.id.categories);
	        holder.photoView = (ImageView) view.findViewById(R.id.photo);
	        
	        view.setTag(holder);
		} else {
		    holder = (ViewHolder) view.getTag();
		}
		
		if(!sight.getPhotos().isEmpty()) {
		    if(photosCache.getPhotoFromCache(cacheKey) == null) {
		        holder.photoView.setImageResource(R.drawable.ic_launcher); // TODO: change to dumb picture
		         new SuntimeSightsListAdapterWorker().
		                execute(new PhotoLoader(sight.getId(), sight.getPhotos().get(0), holder.photoView, PhotoLoader.SIZE_SMALL));
		    } else {
		        holder.photoView.setImageBitmap(photosCache.getPhotoFromCache(cacheKey));
		    }
		}
		
		holder.descrShortView.setText(sight.getDescriptionShort());
		holder.cityView.setText(sight.getCity());
		holder.titleView.setText(sight.getTitle());
		holder.orderView.setText(String.valueOf(position + 1));
		holder.ratingView.setText(String.valueOf(sight.getRating()));
		holder.opinionView.setText(String.valueOf(sight.getOpinion()) + " отзывов");
		holder.categoriesView.setText(SuntimeUtils.categoriesToString(sight.getCategories()));
		
		return view;
	}
	
	private class SuntimeSightsListAdapterWorker extends 
	                    SuntimePhotosWorker {
	       
	    @Override
        protected void onPostExecute(PhotoLoader loader) {
	        loader.applyPhoto();
	        photosCache.addPhotoToCache(SuntimePhotosCache.getKey(loader.sightId, loader.photoName), loader.bitmap);
        } 
	}

}
