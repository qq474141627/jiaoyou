package com.tt.jiaoyou.ui;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tt.jiaoyou.bean.YueUserInfo;
import com.tt.jiaoyou.util.ToastUtils;
import com.tt.jiaoyou.xml.XMLParser;
import com.tt.jiao.you.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Yue extends Activity implements OnClickListener{

	private ImageView user_img;
	private TextView user_name,user_age,user_height,user_photonum,user_distance,user_like_text;
	private ImageView user_yue_not,user_yue_yes;
	private View pView,user_info,user_like;
	private YueUserInfo info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yue);
		TextView actionbar_title = (TextView) findViewById(R.id.actionbar_title);
		actionbar_title.setText(getString(R.string.actionbar_title3));
		TextView actionbar_vip = (TextView) findViewById(R.id.actionbar_vip);
		actionbar_vip.setOnClickListener(this);
		
		user_info = findViewById(R.id.user_info);
		pView = findViewById(R.id.pb);
		user_img = (ImageView)findViewById(R.id.user_img);
		user_name = (TextView)findViewById(R.id.user_name);
		user_age = (TextView)findViewById(R.id.user_age);
		user_height = (TextView)findViewById(R.id.user_height);
		user_photonum = (TextView)findViewById(R.id.user_photonum);
		user_distance = (TextView)findViewById(R.id.user_distance);
		user_like_text = (TextView)findViewById(R.id.user_like_text);
		user_like = findViewById(R.id.user_like);
		user_like.setOnClickListener(this);
		user_yue_not = (ImageView)findViewById(R.id.user_yue_not);
		user_yue_yes = (ImageView)findViewById(R.id.user_yue_yes);
		user_yue_not.setOnClickListener(this);
		user_yue_yes.setOnClickListener(this);
		new MyThread().start();
	}

	class MyThread extends Thread {
		@Override
		public void run() {
			super.run();
			Message message = new Message();
			message.what = 1;
			message.obj = XMLParser.getYueUserInfo();
			handler.sendMessage(message);
		}
	}
	
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			pView.setVisibility(View.GONE);
			user_info.setVisibility(View.VISIBLE);
			info = (YueUserInfo) msg.obj;
			if(info == null){
				ToastUtils.showToast("获取数据失败");
				return;
			}
			user_name.setText(info.getNick());
			user_age.setText(info.getAge()+"岁");
			user_distance.setText("2km");
			user_height.setText(info.getHeight()+"cm");
			user_photonum.setText(info.getPhoto_nums()+"张图片");
			if(info.getGreeted_nums() == 0){
				user_like_text.setText("我好喜欢你");
			}else{
				user_like_text.setText(info.getGreeted_nums()+"人喜欢TA");
			}
			if(info.getUrl() != null)
			    ImageLoader.getInstance().displayImage(info.getUrl().replace("@400w_90Q_1x.jpg", ""),user_img);
		};
	};
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() == R.id.user_yue_not){
			if(pView.getVisibility() == 8){
				//pView.setVisibility(0);
				new MyThread().start();
			}
		}else if(arg0.getId() == R.id.user_yue_yes){
			if(info != null){
				Intent intent = new Intent(Activity_Yue.this,Activity_Photo_Info.class);
				intent.putExtra("user_id", info.getUser_id());
				intent.putExtra("photo_id", info.getPhoto_id());
				startActivity(intent);
			}
		}else if(arg0.getId() == R.id.user_like){
			ToastUtils.showLongToast("点赞成功，赶快给她送份礼物吧!");
		}else if(arg0.getId() == R.id.actionbar_vip ){
			sendBroadcast(new Intent(MainActivity.ACTION_TAB));
		}
	}
}
