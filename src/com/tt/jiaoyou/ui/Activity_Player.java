package com.tt.jiaoyou.ui;


import android.app.Activity;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.tt.jiao.you.R;

public class Activity_Player extends Activity implements MediaPlayer.OnErrorListener, 
    OnCompletionListener , OnPreparedListener, OnBufferingUpdateListener,OnInfoListener{
    public static final String TAG = "VideoPlayer";
    private VideoView mVideoView;
    private Uri mUri;
    private int mPositionWhenPaused = -1;
    private View pView;
    private MediaController mMediaController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
        setContentView(R.layout.activity_player);

        pView = findViewById(R.id.pb);
        pView.findViewById(R.id.progress_text).setVisibility(0);
        mVideoView = (VideoView)findViewById(R.id.video_view);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnErrorListener(this);
        mVideoView.setOnInfoListener(this);
        //Video file
        mUri = Uri.parse(getIntent().getStringExtra("url"));

        //Create media controller，组件可以控制视频的播放，暂停，回复，seek等操作，不需要你实现
        mMediaController = new MediaController(this);
        mVideoView.setMediaController(mMediaController);
    }

    @Override 
    public void onStart() {
        // Play Video
        mVideoView.setVideoURI(mUri);
        mVideoView.start();

        super.onStart();
    }

    @Override 
    public void onPause() {
        // Stop video when the activity is pause.
        mPositionWhenPaused = mVideoView.getCurrentPosition();
        mVideoView.stopPlayback();

        super.onPause();
    }

    @Override 
    public void onResume() {
        // Resume video player
        if(mPositionWhenPaused >= 0) {
            mVideoView.seekTo(mPositionWhenPaused);
            mPositionWhenPaused = -1;
        }

        super.onResume();
    }
    
    @Override    
    protected void onDestroy() {    
        super.onDestroy();    
    }

    public boolean onError(MediaPlayer player, int arg1, int arg2) {
    	pView.setVisibility(8);
    	new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				finish();
			}
		}, 2000);
        return false;
    }

    public void onCompletion(MediaPlayer mp) {
        this.finish();
    }
    
    
    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        int sec = (int) outState.getLong("time");
        if(mVideoView != null)
           mVideoView.seekTo(sec);
        super.onRestoreInstanceState(outState);
        }
     
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        int sec = mVideoView.getCurrentPosition();
        outState.putLong("time", sec);
        super.onSaveInstanceState(outState);
        }
    
	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int arg1) {
		// TODO Auto-generated method stub
		pView.setVisibility(View.GONE);//缓冲完成就隐藏
	}

	@Override
	public void onPrepared(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		arg0.setOnBufferingUpdateListener(this);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		if(what == MediaPlayer.MEDIA_INFO_BUFFERING_START){
            pView.setVisibility(View.VISIBLE);
        }else if(what == MediaPlayer.MEDIA_INFO_BUFFERING_END){
            //此接口每次回调完START就回调END,若不加上判断就会出现缓冲图标一闪一闪的卡顿现象
            if(mp.isPlaying()){
            	pView.setVisibility(View.GONE);
            }
        }
        return true;
	}

}