package com.tt.jiaoyou.adapter;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tt.jiao.you.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tt.jiaoyou.bean.VideoBean;
public class VideoAdpter extends BaseAdapter {
	private Context context;
	public ArrayList<VideoBean> beans ;
	public VideoAdpter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		if(beans != null){
			return beans.size();
		}
		return 0;
	}

	@Override
	public VideoBean getItem(int arg0) {
		if(beans != null){
			return beans.get(arg0);
		}
		return null;
	}
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
//	public void addData(ArrayList<VideoBean> list) {
//		if(list != null){
//			beans.addAll(list);
//			notifyDataSetChanged();
//		}
//	}
	
	public void reflashData(ArrayList<VideoBean> list) {
		beans = list;
		notifyDataSetChanged();
	}
	
//	public void clear() {
//		if(beans != null){
//			beans.clear();
//			notifyDataSetChanged();
//		}
//	}
	
	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		GetView getView;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.item_video_grid, null);
			getView = new GetView();
			getView.item_video_grid_img = (ImageView) view.findViewById(R.id.item_video_grid_img);
			getView.item_video_grid_name = (TextView) view.findViewById(R.id.item_video_grid_name);
			getView.item_video_grid_text = (TextView) view.findViewById(R.id.item_video_grid_text);
			view.setTag(getView);
		} else {
			getView = (GetView) view.getTag();
			getView.item_video_grid_img.setImageResource(R.drawable.ic_female);;
		}
		if(beans == null || beans.size() == 0) return view;
		getView.item_video_grid_name.setText(beans.get(arg0).getNick());
		getView.item_video_grid_text.setText(beans.get(arg0).getText());
		ImageLoader.getInstance().displayImage(beans.get(arg0).getImg(),getView.item_video_grid_img);
		return view;
	}

	static class GetView {
		ImageView item_video_grid_img;
		TextView item_video_grid_name;
		TextView item_video_grid_text;
	}

}
