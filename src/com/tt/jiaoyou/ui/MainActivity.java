package com.tt.jiaoyou.ui;


import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

import com.gogogo.sdk.MainSdk;
import com.tt.jiao.you.R;
import com.tt.jiaoyou.util.MsgUtil;
import com.tt.jiaoyou.util.ToastUtils;

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
	
	@Override  
    public boolean dispatchKeyEvent(KeyEvent event)  
    {  
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK )  
        {  
        	if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {   
        		// 创建退出对话框  
                AlertDialog isExit = new AlertDialog.Builder(this).create();  
                // 设置对话框标题  
                isExit.setTitle("确定要退出吗");  
                // 设置对话框消息  
                isExit.setMessage(getString(R.string.str_exit_text1));  
                // 添加选择按钮并注册监听  
                isExit.setButton("确定", listener);  
                isExit.setButton2("取消", listener);  
                // 显示对话框  
                isExit.show();  
           }  
            return true;  
        }  
          
        return super.dispatchKeyEvent(event);
    } 
	
	/**监听对话框里面的button点击事件*/  
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()  
    {  
        public void onClick(DialogInterface dialog, int which)  
        {  
            switch (which)  
            {  
            case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序  
                finish();  
                break;  
            case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框  
                break;  
            default:  
                break;  
            }  
        }  
    };    

	}
