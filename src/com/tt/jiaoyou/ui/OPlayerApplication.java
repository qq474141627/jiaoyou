package com.tt.jiaoyou.ui;


import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class OPlayerApplication extends Application {

	private static OPlayerApplication mApplication;

	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;
		initImagerLoder();
	}

	public static OPlayerApplication getApplication() {
		return mApplication;
	}

	public static Context getContext() {
		return mApplication;
	}

	/** 销毁 */
	public void destory() {
		mApplication = null;
	}
	private void initImagerLoder()
	{
		DisplayImageOptions  options = new DisplayImageOptions.Builder()            
        .cacheInMemory()                                             
        .cacheOnDisc()                                                   
        .displayer(new RoundedBitmapDisplayer(5))       
        .build();
     ImageLoaderConfiguration config2 = new ImageLoaderConfiguration.Builder(getApplicationContext())
        .threadPriority(Thread.NORM_PRIORITY - 2)
        .defaultDisplayImageOptions(options)
        .denyCacheImageMultipleSizesInMemory()
        .discCacheFileNameGenerator(new Md5FileNameGenerator())
        .tasksProcessingOrder(QueueProcessingType.LIFO)
        .enableLogging() 
        .build();
       ImageLoader.getInstance().init(config2);
       
	}

}
