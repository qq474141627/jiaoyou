package com.tt.jiaoyou.ui;


import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

import com.gogogo.sdk.MainSdk;
import com.tt.jiao.you.R;
import com.tt.jiaoyou.util.MsgUtil;

public class MainActivity extends TabActivity implements OnCheckedChangeListener{
	public static String ACTION_TAB = "action_tab";
	private TabHost mTabHost;
	private RadioGroup radioGroup;
	public static final int	REQUEST_TYPE	= 2014; // 自定义值
	String cpId = "57";// 从商务获取
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化
		MainSdk.init(this.getApplication(), this, cpId);
		
		radioGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioGroup.setOnCheckedChangeListener(this);
		
		mTabHost = getTabHost();
		mTabHost.addTab(mTabHost.newTabSpec("tab1")
				.setIndicator("0")
				.setContent(new Intent(this, Activity_Video.class)));
		mTabHost.addTab(mTabHost.newTabSpec("tab2")
				.setIndicator("1")
				.setContent(new Intent(this, Activity_Photo.class)));
		mTabHost.addTab(mTabHost.newTabSpec("tab3")
				.setIndicator("2")
				.setContent(new Intent(this, Activity_Yue.class)));
		mTabHost.addTab(mTabHost.newTabSpec("tab4")
				.setIndicator("3")
				.setContent(new Intent(this, Activity_Vip.class)));
		mTabHost.setCurrentTab(0); 
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_TAB);
		registerReceiver(receiver, filter);
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int id) {
		// TODO Auto-generated method stub
		if (id == R.id.ic_tab_1) { 
            mTabHost.setCurrentTab(0); 
        } else if (id == R.id.ic_tab_2) { 
            mTabHost.setCurrentTab(1); 
        } else if (id == R.id.ic_tab_3) { 
            mTabHost.setCurrentTab(2); 
        } else if (id == R.id.ic_tab_4) { 
            mTabHost.setCurrentTab(3); 
        } 
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(receiver != null)
		unregisterReceiver(receiver);
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			if(arg1.getAction() == ACTION_TAB){
				Intent intent = new Intent(MainActivity.this,Dialog_tip.class);
				intent.putExtra("msg", MsgUtil.MSG_VIP);
				startActivity(intent);
			}
		}
	};


	}
