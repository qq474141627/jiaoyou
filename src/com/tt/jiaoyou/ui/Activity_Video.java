package com.tt.jiaoyou.ui;

import java.util.ArrayList;

import com.tt.jiao.you.R;
import com.tt.jiaoyou.adapter.VideoAdpter;
import com.tt.jiaoyou.bean.VideoBean;
import com.tt.jiaoyou.util.MsgUtil;
import com.tt.jiaoyou.util.ToastUtils;
import com.tt.jiaoyou.view.PullToRefreshView;
import com.tt.jiaoyou.view.PullToRefreshView.OnFooterRefreshListener;
import com.tt.jiaoyou.view.PullToRefreshView.OnHeaderRefreshListener;
import com.tt.jiaoyou.xml.XMLParser;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Activity_Video extends Activity implements OnItemClickListener ,OnClickListener,
         OnHeaderRefreshListener,OnFooterRefreshListener{

	private VideoAdpter adapter;
	private View pView;
	private GridView mGridView;
	private SharedPreferences preferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		TextView actionbar_title = (TextView) findViewById(R.id.actionbar_title);
		actionbar_title.setText(getString(R.string.actionbar_title1));
		TextView actionbar_vip = (TextView) findViewById(R.id.actionbar_vip);
		actionbar_vip.setOnClickListener(this);
		
		PullToRefreshView pull_refresh_view = (PullToRefreshView) findViewById(R.id.pull_refresh_view);
		//pull_refresh_view.setOnHeaderRefreshListener(this);
		pull_refresh_view.setHeaderRefresh(false);
		pull_refresh_view.setOnFooterRefreshListener(this);
		
		pView = findViewById(R.id.pb);
		mGridView = (GridView) findViewById(R.id.gridView);
		//adapter = new VideoAdpter(this);
		//mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(this);
		new MyThread().start();
	}
	
	class MyThread extends Thread {
		@Override
		public void run() {
			super.run();
			Message message = new Message();
			message.what = 1;
			message.obj = XMLParser.getVideoBeans();
			handler.sendMessage(message);
		}
	}
	
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			pView.setVisibility(View.GONE);
			ArrayList<VideoBean> list = (ArrayList<VideoBean>) msg.obj;
			if (list == null || list.isEmpty()) {
				ToastUtils.showToast("获取数据失败");
				return;
			}
			adapter = new VideoAdpter(Activity_Video.this);
			mGridView.setAdapter(adapter);
			adapter.reflashData(list);
		};
	};
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if(adapter != null && adapter.getItem(arg2) != null){
			String url = adapter.getItem(arg2).getVideo_url();
			if(preferences.getBoolean("pay", false)){
				Intent intent = new Intent(Activity_Video.this,Activity_Player.class);
				intent.putExtra("url", url);
				startActivity(intent);
			}else{
				Intent intent = new Intent(Activity_Video.this,Dialog_tip.class);
				intent.putExtra("url", url);
				intent.putExtra("msg", MsgUtil.MSG_VIDEO1);
				startActivity(intent);
				
				intent.putExtra("msg", MsgUtil.MSG_VIDEO2);
				startActivity(intent);
			}
		}
		}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() == R.id.actionbar_vip ){
			sendBroadcast(new Intent(MainActivity.ACTION_TAB));
		}
	}

	@Override
	public void onHeaderRefresh(final PullToRefreshView view) {
		// TODO Auto-generated method stub
		view.postDelayed(new Runnable() {
			//@Override
			public void run() {
				view.onHeaderRefreshComplete("");
				if(pView.getVisibility() == 8){
					new MyThread().start();
				}
			}
		},1000);
	}

	@Override
	public void onFooterRefresh(final PullToRefreshView view) {
		// TODO Auto-generated method stub
		view.postDelayed(new Runnable() {
			//@Override
			public void run() {
				view.onFooterRefreshComplete();
				if(pView.getVisibility() == 8){
					new MyThread().start();
				}
			}
		},1000);
	}

}
