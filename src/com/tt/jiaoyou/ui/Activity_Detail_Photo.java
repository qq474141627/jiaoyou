package com.tt.jiaoyou.ui;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tt.jiaoyou.adapter.DetailPhotoAdpter;
import com.tt.jiaoyou.util.ToastUtils;
import com.tt.jiao.you.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class Activity_Detail_Photo extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_photo);
		ViewPager vPager = (ViewPager) findViewById(R.id.vPager);
		if(getIntent() != null){
			ArrayList<String> list = getIntent().getStringArrayListExtra("detail_img");
			ToastUtils.showLongToast(list.size() +"张图片");
			List<View> views = new ArrayList<View>();
			for(String string :list){
				View view = LayoutInflater.from(this).inflate(R.layout.item_detail_photo, null);
				views.add(view);
				ImageView image = (ImageView) view.findViewById(R.id.photo_img);
				ImageLoader.getInstance().displayImage(string,image);
			}
			vPager.setAdapter(new DetailPhotoAdpter(views));
		}
	}



}
