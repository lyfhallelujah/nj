package com.example.sound;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

public class musicService extends Service{
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 保持Activity 和Service 的通信
	public final IBinder binder =  new MyBinder();
	public class MyBinder extends Binder {
		musicService getService(){
			return musicService.this;
		}
	}
	
	public static MediaPlayer mp ;
	
	public musicService(){
		
	}
	
	public musicService(String url){
		try{
			mp = new MediaPlayer();
			mp.setDataSource(url);
			mp.prepare();
		}catch (Exception e){
			e.printStackTrace();
			//Log.d("hint","can't get the song");
		}
	} 
	
	public void playOrPause(){
		if(mp.isPlaying())
			mp.pause();
		else 
			mp.start();
	}
	
	public void stop(){
		if(mp!=null){
			mp.stop();
			try{
				mp.prepare();
				mp.seekTo(0);			
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onDestroy(){
		mp.stop();
		mp.release();
		super.onDestroy();
	}
	
}
