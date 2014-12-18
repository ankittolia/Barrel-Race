package com.barrel.adapter;

/**
 * @className CS 6301-022 User Interface Design
 * @author Ankit Tolia (abt140130)
 * @email-id abt140130@utdallas.edu
 * @version 1.0
 * @started date 30 Nov 2014
 * @Description : Barrel Race Game for an Android Device
 * @Purpose : Class Assignment
 */

import java.util.List;

import com.barrel.bean.HighScore;
import com.barrel.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

/* Adaptor Class for Listview that is notified for any changes in list and updates the listview data accordingly.
 * Purpose : Class Assignment
 */
public class CustomListViewAdapter extends ArrayAdapter<HighScore> {

	Context context;

	public CustomListViewAdapter(Context context, int resourceId,
			List<HighScore> items) {
		super(context, resourceId, items);
		this.context = context;
	}

	/* private view holder class that displays the child items of listview */
	private class ViewHolder {
		TextView txtName;
		TextView txtTime;
		ImageView img;
		TextView rank;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		HighScore rowItem = getItem(position);
		// Set Child View Data from the Bean
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.txtName = (TextView) convertView.findViewById(R.id.name);
			holder.txtTime = (TextView) convertView
					.findViewById(R.id.timetaken);
			holder.img = (ImageView) convertView.findViewById(R.id.imgLogo);
			holder.rank = (TextView) convertView.findViewById(R.id.rank);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		if (rowItem.getName() != null && !rowItem.getName().trim().isEmpty())
			holder.txtName.setText(rowItem.getName()); // show the user name
		else
			holder.txtName.setText(context.getResources().getString(
					R.string.user_name));

		if (rowItem.getTime() != null) {
			int seconds = (int) (rowItem.getTime() / 1000);
			int minutes = seconds / 60;
			seconds = seconds % 60;
			holder.txtTime.setText(minutes + ":"
					+ String.format("%02d", seconds)); // show time completed
		} else
			holder.txtTime.setText("");
		if (rowItem.getImage() == 1) {
			holder.img.setVisibility(View.VISIBLE);
			holder.rank.setVisibility(View.GONE); // show the image for top 3
													// scorers
		} else {
			holder.img.setVisibility(View.GONE);
			holder.rank.setVisibility(View.VISIBLE);
			holder.rank.setText((int) position + 1 + ""); // show rank for rest

		}
		return convertView;
	}
}