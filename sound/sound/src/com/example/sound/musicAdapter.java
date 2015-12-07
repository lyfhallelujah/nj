package com.example.sound;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class musicAdapter extends ArrayAdapter<music>{
	private int resourceId;
	public musicAdapter(Context context,
			int textViewResourceId,
			List<music>objects){
		super(context,textViewResourceId,objects);
		resourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	       music m = getItem(position); // get the current Fruit ÊµÀý
	
	      //load the xml into the memory
	      View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
	      TextView musicName = (TextView) view.findViewById(R.id.music_name);
	      
	      musicName.setText(m.getName());
	      return view;
	 }
}
