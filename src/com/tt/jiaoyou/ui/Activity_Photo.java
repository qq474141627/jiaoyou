package com.tt.jiaoyou.ui;

import java.util.ArrayList;

import com.tt.jiao.you.R;
import com.tt.jiaoyou.adapter.PhotoAdpter;
import com.tt.jiaoyou.bean.PhotoBean;
import com.tt.jiaoyou.util.ToastUtils;
import com.tt.jiaoyou.view.PullToRefreshView;
import com.tt.jiaoyou.view.PullToRefreshView.OnFooterRefreshListener;
import com.tt.jiaoyou.view.PullToRefreshView.OnHeaderRefreshListener;
import com.tt.jiaoyou.xml.XMLParser;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Activity_Photo extends Activity implements OnItemClickListener ,OnClickListener,
         OnHeaderRefreshListener,OnFooterRefreshListener{

	private PhotoAdpter adapter;
	private View pView;
	private GridView mGridView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		TextView actionbar_title = (TextView) findViewById(R.id.actionbar_title);
		actionbar_title.setText(getString(R.string.actionbar_title2));
		TextView actionbar_vip = (TextView) findViewById(R.id.actionbar_vip);
		actionbar_vip.setOnClickListener(this);
		
		PullToRefreshView pull_refresh_view = (PullToRefreshView) findViewById(R.id.pull_refresh_view);
		//pull_refresh_view.setOnHeaderRefreshListener(this);
		pull_refresh_view.setHeaderRefresh(false);
		pull_refresh_view.setOnFooterRefreshListener(this);
		
		pView = findViewById(R.id.pb);
		mGridView = (GridView) findViewById(R.id.gridView);
//		adapter = new PhotoAdpter(this);
//		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(this);
		new MyThread().start();
	}
	
	class MyThread extends Thread {
		@Override
		public void run() {
			super.run();
			Message message = new Message();
			message.what = 1;
			message.obj = XMLParser.getPhotoBeans("1", 1);
			handler.sendMessage(message);
		}
	}
	
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			pView.setVisibility(View.GONE);
			ArrayList<PhotoBean> list = (ArrayList<PhotoBean>) msg.obj;
			if (list == null || list.isEmpty()) {
				ToastUtils.showToast("获取数据失败");
				return;
			}
			adapter = new PhotoAdpter(Activity_Photo.this);
			mGridView.setAdapter(adapter);
			adapter.reflashData(list);
		};
	};
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if(adapter != null && adapter.getItem(arg2) != null){
			Intent intent = new Intent(Activity_Photo.this,Activity_Photo_Info.class);
			intent.putExtra("user_id", adapter.getItem(arg2).getUser_id());
			intent.putExtra("photo_id", adapter.getItem(arg2).getPhoto_id());
			startActivity(intent);
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
