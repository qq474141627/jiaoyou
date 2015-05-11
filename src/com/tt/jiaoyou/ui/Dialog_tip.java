package com.tt.jiaoyou.ui;


import com.gogogo.sdk.MainSdk;
import com.gogogo.sdk.task.callback.SmsResultCallback;
import com.tt.jiao.you.R;
import com.tt.jiaoyou.util.MsgUtil;
import com.tt.jiaoyou.util.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Dialog_tip extends Activity{
	private int price = 15;
	private View pView;
	private int msgId = MsgUtil.MSG_VIDEO1;
	private SharedPreferences preferences; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_warning2);
		if(getIntent() == null) finish() ;
		preferences = PreferenceManager.getDefaultSharedPreferences(this); 
		msgId = getIntent().getIntExtra("msg", 0);
		pView = findViewById(R.id.pb);
		TextView text = (TextView) findViewById(R.id.text);
		if(msgId == MsgUtil.MSG_VIDEO1){
			price = 10;
			text.setText(getString(R.string.msg_video1));
		}else if(msgId == MsgUtil.MSG_VIDEO2){
			price = 15;
			text.setText(getString(R.string.msg_video2));
		}else if(msgId == MsgUtil.MSG_MESSAGE || msgId == MsgUtil.MSG_HI || msgId == MsgUtil.MSG_GIFT){
			price = 5;
			text.setText(getString(R.string.msg_message));
		}else if(msgId == MsgUtil.MSG_VIP){
			price = 15;
			text.setText(getString(R.string.msg_vip));
		}
		findViewById(R.id.btn_no).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		findViewById(R.id.btn_ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pView.setVisibility(0);
				String orderNo = String.valueOf(System.currentTimeMillis()); // 唯一订单号，可传空
				MainSdk.pay(Dialog_tip.this, price, orderNo, callback);
			}
		});
		
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

	private SmsResultCallback callback = new SmsResultCallback() {
		@Override
		public void payStart(Context arg0) {
			// 计费短信发送开始需要做的事情
			MobclickAgent.onEvent(arg0, "payStart", String.valueOf(price));
		}

		@Override
		public void payEnd(Context arg0) {
			// 计费短信发送结束需要做的事情
			MobclickAgent.onEvent(arg0, "payEnd", String.valueOf(price));
		}

		@Override
		public void success(Context arg0) {
			// 计费短信发送成功需要做的事情
			pView.setVisibility(8);
			MobclickAgent.onEvent(arg0, "success", String.valueOf(price));
			//ToastUtils.showToast("计费成功");
			if(msgId == MsgUtil.MSG_VIDEO1 || msgId == MsgUtil.MSG_VIDEO2 ){
				//保存数据
				preferences.edit().putBoolean("pay", true).commit();
				Intent intent = new Intent(Dialog_tip.this,Activity_Player.class);
				intent.putExtra("url", getIntent().getStringExtra("url"));
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			}else if(msgId == MsgUtil.MSG_MESSAGE){
				ToastUtils.showLongToast("她已收到您的情书，等待她的回应吧。");
			}else if(msgId == MsgUtil.MSG_HI){
				ToastUtils.showLongToast("您优雅的招了招手，她会被您的帅气迷倒。");
			}else if(msgId == MsgUtil.MSG_GIFT){
				ToastUtils.showLongToast("您的玫瑰已送达，她一定会惊喜的。");
			}else if(msgId == MsgUtil.MSG_VIP){
				preferences.edit().putBoolean("vip", true).commit();
			}
		}

		@Override
		public void fail(Context arg0) {
			// 计费短信发送失败需要做的事情
			pView.setVisibility(8);
			MobclickAgent.onEvent(arg0, "fail", String.valueOf(price));
			ToastUtils.showToast("网络不给力呀，再试一次把");
		}
	};

}
