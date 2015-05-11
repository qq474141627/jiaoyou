package com.tt.jiaoyou.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.tt.jiao.you.R;
import com.umeng.analytics.MobclickAgent;

public class Activity_Welcome extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		MobclickAgent.updateOnlineConfig(this);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				startActivity(new Intent(Activity_Welcome.this,MainActivity.class));
				overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
				finish();
			}
		}, 2000);
	}
	
    
    public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		}
	
	@Override
	   public boolean onKeyDown(int keyCode, KeyEvent event)
	   {
	       if (keyCode == KeyEvent.KEYCODE_BACK)
	       {
	   				return true;
	       }
	       return super.onKeyDown(keyCode, event);
	   }
	
}
