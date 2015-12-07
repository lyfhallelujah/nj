package com.example.sound;


import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class DetailActivity extends Activity implements View.OnClickListener{
	TextView state;
	TextView t;
	SeekBar seekBar;
	Button pp, st;//play_pause stop
	musicService ms;
	private SimpleDateFormat time = new SimpleDateFormat("m:ss");
		
	ServiceConnection sc = new ServiceConnection(){
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			ms = ((musicService.MyBinder) service).getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			 ms = null;
		}		
	};
	
	private void bindServiceConnection(){
		Intent intent = new Intent(DetailActivity.this,musicService.class);
		startService(intent);
		bindService(intent,sc,this.BIND_AUTO_CREATE);
	}	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         requestWindowFeature(Window.FEATURE_NO_TITLE);
         
         setContentView(R.layout.activity_detail);//页面布局
         
         state = (TextView)findViewById(R.id.music_state);
     	 t = (TextView)findViewById(R.id.music_time);
     	 seekBar = (SeekBar)findViewById(R.id.music_time2);
     	 pp = (Button)findViewById(R.id.play_pause_music);
     	 st = (Button)findViewById(R.id.stop_music);         
         
     	 pp.setOnClickListener(this);
     	 st.setOnClickListener(this);
         
         Bundle myBundelForGetName=this.getIntent().getExtras(); 
         String name = myBundelForGetName.getString("music_name"); 
         String url = myBundelForGetName.getString("music_url");
         
         ms = new musicService(url);
     	 bindServiceConnection(); 
         
         //display the distance
         TextView t1 = (TextView) findViewById(R.id.music_name_detail);                  
         t1.setVisibility(View.VISIBLE);
         t1.setText(name); 
         
         TextView t2 = (TextView) findViewById(R.id.music_url_detail);                  
         t2.setVisibility(View.VISIBLE);
         t2.setText(url);
         
         //jump back to the mainActivity
         Button bn = (Button)findViewById(R.id.back_to_main);
         bn.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View view) {
            	 
            	handler.removeCallbacks(r);
            	ms.playOrPause();
            	ms.onDestroy();
 		    	unbindService(sc);
            	 	 
          	   Intent intent = new Intent(DetailActivity.this, MainActivity.class);
             	   startActivity(intent);               
             }
         });
    }
	
	
	//实时更新组建状态
	Handler handler = new Handler();
	Runnable r = new Runnable(){
		@Override
		public void run(){
			if(musicService.mp.isPlaying()){
				state.setText("Playing");			
			} else{
				state.setText("Pausing");
			}
			
			t.setText(time.format(musicService.mp.getCurrentPosition())+'/'+
					  time.format(musicService.mp.getDuration()));
			
		    seekBar.setProgress(musicService.mp.getCurrentPosition());
		    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub					
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub					
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					// TODO Auto-generated method stub
					if(fromUser)
						musicService.mp.seekTo(seekBar.getProgress());
				}
			});
			handler.postDelayed(r, 100);
		}
	};
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()){
		    case R.id.play_pause_music:
		    	ms.playOrPause();
		    	break;
		    case R.id.stop_music:
		    	ms.stop();
		    	seekBar.setProgress(0);
		    	break;
		    default:
		    	break;
		    
		}
	}
	
	@Override
    protected void onResume(){
		if(musicService.mp.isPlaying()){
			state.setText("Playing");			
		} else{
			state.setText("Pausing");
		}
		seekBar.setProgress(musicService.mp.getCurrentPosition());
		seekBar.setMax(musicService.mp.getDuration());
		handler.post(r);
		super.onResume();
	}	
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		unbindService(sc);
	}
}
