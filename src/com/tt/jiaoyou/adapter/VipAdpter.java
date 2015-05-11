package com.tt.jiaoyou.adapter;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tt.jiao.you.R;
import com.tt.jiaoyou.bean.VipBean;
public class VipAdpter extends BaseAdapter {
	private Context context;
	public List<VipBean> beans ;
	public VipAdpter(Context context) {
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
	public VipBean getItem(int arg0) {
		if(beans != null){
			return beans.get(arg0);
		}
		return null;
	}
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
//	public void addData(ArrayList<VipBean> list) {
//		if(list != null){
//			beans.addAll(list);
//			notifyDataSetChanged();
//		}
//	}
	
	public void reflashData(List<VipBean> list) {
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
			view = LayoutInflater.from(context).inflate(R.layout.item_vip_list, null);
			getView = new GetView();
			getView.vip_left = (TextView) view.findViewById(R.id.vip_left);
			getView.vip_right = (TextView) view.findViewById(R.id.vip_right);
			getView.vip_text = (TextView) view.findViewById(R.id.vip_text);
			view.setTag(getView);
		} else {
			getView = (GetView) view.getTag();
		}
		if(beans == null || beans.size() == 0) return view;
		VipBean bean = beans.get(arg0);
		Log.i("tag", "--------vip_left = "+bean.getLeft());
		Log.i("tag", "--------vip_right = "+bean.getName());
		Log.i("tag", "--------vip_text = "+bean.getText());
		getView.vip_left.setText(bean.getLeft());
		getView.vip_right.setText(bean.getName());
		getView.vip_text.setText(bean.getText());
		return view;
	}

	static class GetView {
		TextView vip_left;
		TextView vip_right;
		TextView vip_text;
	}

}
