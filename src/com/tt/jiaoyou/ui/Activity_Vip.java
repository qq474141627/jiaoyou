package com.tt.jiaoyou.ui;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tt.jiaoyou.adapter.SenderAdapter;
import com.tt.jiaoyou.adapter.VipAdpter;
import com.tt.jiaoyou.bean.VipBean;
import com.tt.jiaoyou.ui.Activity_Photo_Info.MyThread;
import com.tt.jiaoyou.util.ToastUtils;
import com.tt.jiaoyou.xml.XMLParser;
import com.tt.jiao.you.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class Activity_Vip extends Activity implements OnItemClickListener,OnClickListener{

	private View pView;
	private VipAdpter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vip);
		TextView actionbar_title = (TextView) findViewById(R.id.actionbar_title);
		actionbar_title.setText(getString(R.string.actionbar_title4));
		TextView actionbar_vip = (TextView) findViewById(R.id.actionbar_vip);
		actionbar_vip.setOnClickListener(this);
		
		pView = findViewById(R.id.pb);
		ListView mListView = (ListView) findViewById(R.id.listView);
		adapter = new VipAdpter(this);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
		new MyThread().start();
	}
	
	class MyThread extends Thread {
		@Override
		public void run() {
			super.run();
			Message message = new Message();
			message.obj = XMLParser.getVipInfo();
			message.what = 1;
			handler.sendMessage(message);
		}
	}
	
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			pView.setVisibility(View.GONE);
			List<VipBean> list = (List<VipBean>) msg.obj;
			if(list == null || list.size() == 0){
				ToastUtils.showToast("获取数据失败");
				return;
			}
			adapter.reflashData(list);
			
		};
	};
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		ToastUtils.showToast(adapter.getItem(arg2).getRmb()+"");
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() == R.id.actionbar_vip ){
			sendBroadcast(new Intent(MainActivity.ACTION_TAB));
		}
	}


}
