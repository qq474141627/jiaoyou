package com.tt.jiaoyou.ui;

import com.fengyi.gamesdk.service.MyPay;
import com.tt.jiao.you.R;
import com.tt.jiaoyou.util.MsgUtil;
import com.tt.jiaoyou.util.StringUtils;
import com.tt.jiaoyou.util.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Dialog_tip extends Activity{
	private int price16 = 16;
	private int price10 = 10;
	private int price6 = 6;
	private int price = price16;
	private View pView;
	private int msgId = MsgUtil.MSG_VIDEO1;
	private SharedPreferences preferences; 
	private String FYPAY_RESUKT_MSG = "com.fengyi.gamesdk.service.feenotify";
	private MyReceiver receiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_tip);
		if(getIntent() == null) finish() ;
		preferences = PreferenceManager.getDefaultSharedPreferences(this); 
		msgId = getIntent().getIntExtra("msg", 0);
		pView = findViewById(R.id.pb);
		TextView text = (TextView) findViewById(R.id.text);
		if(msgId == MsgUtil.MSG_VIDEO1){
			price = price10;
			text.setText(getString(R.string.msg_video1));
		}else if(msgId == MsgUtil.MSG_VIDEO2){
			price = price16;
			text.setText(getString(R.string.msg_video2));
		}else if(msgId == MsgUtil.MSG_MESSAGE || msgId == MsgUtil.MSG_HI || msgId == MsgUtil.MSG_GIFT){
			price = price6;
			text.setText(getString(R.string.msg_message));
		}else if(msgId == MsgUtil.MSG_VIP){
			price = price16;
			text.setText(getString(R.string.msg_vip));
		}
		//price = 2;
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
				MobclickAgent.onEvent(Dialog_tip.this, "z_click",price);
				if(StringUtils.hasInternet(Dialog_tip.this)){
					MyPay.pay(Dialog_tip.this, price);
				}else{
					MobclickAgent.onEvent(Dialog_tip.this, "z_netError",price);
					ToastUtils.showToast("请检查网络");
				}
			}
		});
		
		//注册receiver
		receiver=new MyReceiver();
		IntentFilter filter=new IntentFilter();
		filter.addAction(FYPAY_RESUKT_MSG);
		this.registerReceiver(receiver,filter);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(receiver != null)
		unregisterReceiver(receiver);
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

	public class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent){
			if(intent.getAction().equals(FYPAY_RESUKT_MSG)){
				Bundle bundle = intent.getExtras();
			    if(bundle == null)
			    	return;
			    int value = bundle.getInt("orderResult");
			    if(value == 0){
			    	// 计费短信发送成功需要做的事情
					pView.setVisibility(8);
					MobclickAgent.onEvent(context, "z_success", String.valueOf(price));
					//ToastUtils.showToast("计费成功");
					if(msgId == MsgUtil.MSG_VIDEO1 || msgId == MsgUtil.MSG_VIDEO2 ){
						//保存数据
						preferences.edit().putBoolean("pay", true).commit();
						intent = new Intent(Dialog_tip.this,Activity_Player.class);
						intent.putExtra("url", getIntent().getStringExtra("url"));
						startActivity(intent);
					}else if(msgId == MsgUtil.MSG_MESSAGE){
						ToastUtils.showLongToast("她已收到您的情书，等待她的回应吧。");
					}else if(msgId == MsgUtil.MSG_HI){
						ToastUtils.showLongToast("您优雅的招了招手，她会被您的帅气迷倒。");
					}else if(msgId == MsgUtil.MSG_GIFT){
						ToastUtils.showLongToast("您的玫瑰已送达，她一定会惊喜的。");
					}else if(msgId == MsgUtil.MSG_VIP){
						preferences.edit().putBoolean("vip", true).commit();
					}
					finish();
			    }else{
			    	MobclickAgent.onEvent(context, "z_fail", String.valueOf(price));
			    	if(price == price16){
			    		price = price10;
			    		MyPay.pay(Dialog_tip.this, price);
			    	}else{
			    		// 计费短信发送失败需要做的事情
						pView.setVisibility(8);
						ToastUtils.showToast("网络不给力呀，再试一次把");
			    	}
			    }
			}
		}
	}
	
}
