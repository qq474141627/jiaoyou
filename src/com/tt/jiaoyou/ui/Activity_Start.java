package com.tt.jiaoyou.ui;


import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.tt.jiao.you.R;
import com.tt.jiaoyou.util.StringUtils;

public class Activity_Start extends Activity{
	private View progress_bar,dialog_warning,dialog_libao;
	private SharedPreferences preferences;
	private String paytime = "time";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		progress_bar = findViewById(R.id.pb);
		showWarningDialog();
		
	}
	
	private void showWarningDialog(){
		dialog_warning = findViewById(R.id.dialog_warning);
		findViewById(R.id.btn_warning).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(preferences.getString(paytime,"").equals(StringUtils.getDate())){
					startActivity(new Intent(Activity_Start.this, MainActivity.class));
			    	overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			    	finish();
				}else{
					dialog_warning.setVisibility(8);
					showLibaoDialog();
				}
			}
		});
	}
	private void showLibaoDialog(){
		dialog_libao = findViewById(R.id.dialog_libao);
		dialog_libao.setVisibility(0);
		findViewById(R.id.btn_libao).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				progress_bar.setVisibility(0);
				//点击统计
			}
		});
	}
	
	private long exitTime = 0;
	   @Override
	   public boolean onKeyDown(int keyCode, KeyEvent event)
	   {
		   if (keyCode == KeyEvent.KEYCODE_BACK)
	       {
	    	   if ((System.currentTimeMillis() - exitTime) > 1000)
	           {
	                   Toast.makeText(this, getString(R.string.exit_click), Toast.LENGTH_SHORT).show();
	                   exitTime = System.currentTimeMillis();
	           } else
	           {
	        	   finish();
	           }
	   			return true;
	       }
	       return super.onKeyDown(keyCode, event);
	   }
	   
}
