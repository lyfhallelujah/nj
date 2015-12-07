package com.example.sound;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class BaiduActivity extends Activity{
	
	WebView myWebView;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         requestWindowFeature(Window.FEATURE_NO_TITLE);
         
         setContentView(R.layout.activity_baidu);//ҳ�沼��
         
         
		Button bn = (Button)findViewById(R.id.baidu_back_to_main);
	    bn.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View view) {
	       	
	     	   Intent intent = new Intent(BaiduActivity.this, MainActivity.class);
	        	   startActivity(intent);               
	        }
	    });
	    
	    Bundle myBundelForGetName=this.getIntent().getExtras(); 
        String name = myBundelForGetName.getString("music_name"); 
        String url = myBundelForGetName.getString("music_url");
	    
        TextView mT = (TextView) findViewById(R.id.music_title);
        mT.setText(name);
        
	    myWebView = (WebView) findViewById(R.id.webview);
	    myWebView.loadUrl(url);
	    WebSettings webSettings = myWebView.getSettings();
	    webSettings.setJavaScriptEnabled(true);
	    
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
    @Override
    protected void onPause ()
    {
    	myWebView.onPause();
    	WebSettings webSettings = myWebView.getSettings();
	    webSettings.setJavaScriptEnabled(false);
	    myWebView.loadUrl("www.baidu.com");
    	super.onPause ();
    }
    
    @Override
    protected void onDestroy ()
    {
    	myWebView.onPause();
    	WebSettings webSettings = myWebView.getSettings();
	    webSettings.setJavaScriptEnabled(false);
	    myWebView.loadUrl("www.baidu.com");
    	super.onDestroy ();
    }
    
}
