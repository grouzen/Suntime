package ua.com.suntime;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SightsListAdapter extends ArrayAdapter<SuntimeSight> {

	protected final Context context;
	protected ArrayList<SuntimeSight> sights;
	protected final int rowViewResourceId; 
	
	public SightsListAdapter(Context context, int rowViewResourceId, ArrayList<SuntimeSight> sights) {
		super(context, rowViewResourceId, sights);
		
		this.context = context;
		this.sights = sights;
		this.rowViewResourceId = rowViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(rowViewResourceId, parent, false);
		TextView descrShortView = (TextView) view.findViewById(R.id.description_s);
		TextView cityView = (TextView) view.findViewById(R.id.city);
		TextView titleView = (TextView) view.findViewById(R.id.title);
		
		descrShortView.setText(sights.get(position).getDescriptionShort());
		cityView.setText(sights.get(position).getCity());
		titleView.setText(sights.get(position).getTitle());
		
		return view;
	}

}
