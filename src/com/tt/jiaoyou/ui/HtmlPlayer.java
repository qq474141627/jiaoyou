package com.tt.jiaoyou.ui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tt.jiao.you.R;


public class HtmlPlayer extends Activity{
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(
    				WindowManager.LayoutParams.FLAG_FULLSCREEN,
    				WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setFlags(
    				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
    				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            
            setContentView(R.layout.htmlplayer);
            
            mWebView = (WebView) findViewById(R.id.webView);
        	mWebView.setWebChromeClient(new WebChromeClient());
        	mWebView.setWebViewClient(new WebViewClient());
        	WebSettings webSetting=mWebView.getSettings();
        	webSetting.setJavaScriptEnabled(true);
        	webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        	webSetting.setUseWideViewPort(true);
        	webSetting.setLoadWithOverviewMode(true); 
        	if(getIntent() != null){
//        		mWebView.loadUrl(XMLUtil.goToPay(getIntent().getIntExtra("amount", 10),
//						getIntent().getStringExtra("goodsName")));
//        		mWebView.postUrl(url, EncodingUtils.getBytes(
//        				XMLUtil.goToPay(getIntent().getIntExtra("amount", 10),
//        						getIntent().getStringExtra("goodsName")), "BASE64"));
        	}
    }
    
    public static void startHtmlPlayer(Context context,int amount ,String goodsName){
    	Intent intent = new Intent(context, HtmlPlayer.class);
    	intent.putExtra("amount", amount);
    	intent.putExtra("goodsName", goodsName);
    	context.startActivity(intent);
    }
    
    @Override
    protected void onDestroy() {
            // TODO Auto-generated method stub
    	super.onDestroy();
    	mWebView.setWebChromeClient(null);
    	mWebView.setWebViewClient(null);
    	mWebView.loadUrl("about:blank");
        mWebView.stopLoading();
        mWebView=null;
    }
 
}
