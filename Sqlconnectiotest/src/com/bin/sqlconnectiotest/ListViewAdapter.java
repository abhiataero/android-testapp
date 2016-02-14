package com.bin.sqlconnectiotest;

import java.util.List;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends ArrayAdapter<Complaints> {
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	List<Complaints> complaintslist;
	private SparseBooleanArray mSelectedItemsIds;
 
	public ListViewAdapter(Context context, int resourceId,
			List<Complaints> complaintslist) {
		super(context, resourceId, complaintslist);
		mSelectedItemsIds = new SparseBooleanArray();
		this.context = context;
		this.complaintslist = complaintslist;
		inflater = LayoutInflater.from(context);
	}
 
	private class ViewHolder {
		TextView rank;
		TextView country;
		TextView population;
		ImageView flag;
	}
 
	public View getView(int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.listview_item, null);
			// Locate the TextViews in listview_item.xml
			holder.rank = (TextView) view.findViewById(R.id.rank);
			holder.country = (TextView) view.findViewById(R.id.country);
			holder.population = (TextView) view.findViewById(R.id.population);
			// Locate the ImageView in listview_item.xml
			holder.flag = (ImageView) view.findViewById(R.id.flag);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Capture position and set to the TextViews
		holder.rank.setText(complaintslist.get(position).getcomplaintid());
		holder.country.setText(complaintslist.get(position).getcomplainddate()+"\n"+complaintslist.get(position).getcomplainttime()+"\n");
		holder.population.setText(complaintslist.get(position)
				.getcomplaintdescription());
		// Capture position and set to the ImageView
		holder.flag.setImageBitmap(complaintslist.get(position)
				.getimge());
		return view;
	}
 
	@Override
	public void remove(Complaints object) {
		complaintslist.remove(object);
		notifyDataSetChanged();
	}
 
	public List<Complaints> getWorldPopulation() {
		return complaintslist;
	}
 
	public void toggleSelection(int position) {
		selectView(position, !mSelectedItemsIds.get(position));
	}
 
	public void removeSelection() {
		mSelectedItemsIds = new SparseBooleanArray();
		notifyDataSetChanged();
	}
 
	public void selectView(int position, boolean value) {
		if (value)
			mSelectedItemsIds.put(position, value);
		else
			mSelectedItemsIds.delete(position);
		notifyDataSetChanged();
	}
 
	public int getSelectedCount() {
		return mSelectedItemsIds.size();
	}
 
	public SparseBooleanArray getSelectedIds() {
		return mSelectedItemsIds;
	}
}