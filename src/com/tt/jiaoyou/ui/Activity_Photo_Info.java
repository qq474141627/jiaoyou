package com.tt.jiaoyou.ui;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tt.jiao.you.R;
import com.tt.jiaoyou.adapter.SenderAdapter;
import com.tt.jiaoyou.bean.Comment;
import com.tt.jiaoyou.bean.PhotoUserInfo;
import com.tt.jiaoyou.util.MsgUtil;
import com.tt.jiaoyou.util.StringUtils;
import com.tt.jiaoyou.util.ToastUtils;
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
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Activity_Photo_Info extends Activity implements OnClickListener{

	private SenderAdapter adapter;
	private View pView;
	private int user_id ;
	private int photo_id ;
	private ImageView detail_img;
	private Comment coment;
	private TextView detail_intro,detail_city,detail_time,
	    detail_login,detail_contact,detail_sms,detail_call,detail_gift,detail_save;
	private PhotoUserInfo info;
	private SharedPreferences preferences;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_info);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		TextView actionbar_title = (TextView) findViewById(R.id.actionbar_title);
		actionbar_title.setText(getString(R.string.actionbar_photoinfo));
		TextView actionbar_vip = (TextView) findViewById(R.id.actionbar_vip);
		actionbar_vip.setOnClickListener(this);
		
		detail_intro = (TextView) findViewById(R.id.detail_intro);
		detail_city = (TextView) findViewById(R.id.detail_city);
		detail_time = (TextView) findViewById(R.id.detail_time);
		detail_login = (TextView) findViewById(R.id.detail_login);
		detail_contact = (TextView) findViewById(R.id.detail_contact);
		detail_sms = (TextView) findViewById(R.id.detail_sms);
		detail_call = (TextView) findViewById(R.id.detail_call);
		detail_save = (TextView) findViewById(R.id.detail_save);
		detail_gift = (TextView) findViewById(R.id.detail_gift);
		detail_save.setOnClickListener(this);
		detail_sms.setOnClickListener(this);
		detail_gift.setOnClickListener(this);
		detail_call.setOnClickListener(this);
		
		pView = findViewById(R.id.pb);
		ListView mListView = (ListView) findViewById(R.id.listView);
		adapter = new SenderAdapter(this);
		mListView.setAdapter(adapter);
		if(getIntent() != null){
			user_id = getIntent().getIntExtra("user_id",1251);
			photo_id = getIntent().getIntExtra("photo_id",33704);
		}
		detail_img = (ImageView) findViewById(R.id.detail_img);
		detail_img.setOnClickListener(this);
		new MyThread().start();
	}
	
	class MyThread extends Thread {
		@Override
		public void run() {
			super.run();
			//info = XMLParser.getPhotoUserInfo(user_id);
			coment = XMLParser.getComment(user_id, photo_id);
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	}
	
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			pView.setVisibility(View.GONE);
			if(info == null && coment == null){
				ToastUtils.showToast("获取数据失败");
				return;
			}
			boolean hasPhoto = false;
			if(info != null){
				detail_intro.setText(info.getIntro());
				detail_contact.setText(info.getPurpose());
				if(info.getShows() != null && info.getShows().size()>0){
					ImageLoader.getInstance().displayImage(info.getShows().get(0),detail_img);
					hasPhoto = true;
				}else if(info.getPhoto() != null && info.getPhoto().size()>0){
					ImageLoader.getInstance().displayImage(info.getPhoto().get(0),detail_img);
					hasPhoto = true;
				}
			}
			if(coment != null){
                adapter.addData(coment.getList());
                if(!hasPhoto){
                	if(coment.getPhoto() != null && coment.getPhoto().size() > 0)
    				    ImageLoader.getInstance().displayImage(coment.getPhoto().get(0),detail_img);
    				else
    					ImageLoader.getInstance().displayImage(coment.getIcon(),detail_img);
                }
			}
			
			if(preferences.getBoolean("vip", false)){
				detail_city.setText("小吃街");
				detail_time.setText("2015-6-27");
				detail_login.setText("今天");
				detail_contact.setText("暂未填写");
			}
			
		};
	};
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() == R.id.detail_img){
			if(info != null && info.getShows() != null && info.getShows().size()>0){
				Intent intent = new Intent(Activity_Photo_Info.this,Activity_Detail_Photo.class);
				intent.putStringArrayListExtra("detail_img", (ArrayList<String>) info.getShows());
				startActivity(intent);
			}else if(info != null && info.getPhoto() != null && info.getPhoto().size()>0){
				Intent intent = new Intent(Activity_Photo_Info.this,Activity_Detail_Photo.class);
				intent.putStringArrayListExtra("detail_img", (ArrayList<String>) info.getPhoto());
				startActivity(intent);
			}else if(coment !=null && coment.getPhoto() != null && coment.getPhoto().size() > 0){
				Intent intent = new Intent(Activity_Photo_Info.this,Activity_Detail_Photo.class);
				intent.putStringArrayListExtra("detail_img", (ArrayList<String>) coment.getPhoto());
				startActivity(intent);
			}
		}else if(arg0.getId() == R.id.detail_sms){
			if(preferences.getBoolean("vip", false)){
				ToastUtils.showLongToast("她已收到您的情书，等待她的回应吧。");
			}else{
				Intent intent = new Intent(Activity_Photo_Info.this,Dialog_tip.class);
				intent.putExtra("msg", MsgUtil.MSG_MESSAGE);
				startActivity(intent);
			}
		}else if(arg0.getId() == R.id.detail_call){
		}else if(arg0.getId() == R.id.detail_save){
			if(preferences.getBoolean("vip", false)){
				ToastUtils.showLongToast("您优雅的招了招手，她会被您的帅气迷倒。");
			}else{
				Intent intent = new Intent(Activity_Photo_Info.this,Dialog_tip.class);
				intent.putExtra("msg", MsgUtil.MSG_HI);
				startActivity(intent);
			}
		}else if(arg0.getId() == R.id.detail_gift){
			if(preferences.getBoolean("vip", false)){
				ToastUtils.showLongToast("您的玫瑰已送达，她一定会惊喜的。");
			}else{
				Intent intent = new Intent(Activity_Photo_Info.this,Dialog_tip.class);
				intent.putExtra("msg", MsgUtil.MSG_GIFT);
				startActivity(intent);
			}
		}else if(arg0.getId() == R.id.actionbar_vip ){
			sendBroadcast(new Intent(MainActivity.ACTION_TAB));
//			finish();
		}
	}
	

}
