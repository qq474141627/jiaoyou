package com.tt.jiaoyou.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tt.jiaoyou.bean.Sender;
import com.tt.jiao.you.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class SenderAdapter extends BaseAdapter{
	private List<Sender> beans = new ArrayList<Sender>();
	private LayoutInflater inflater;
	public SenderAdapter(Context context){
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		if(beans==null)return 0;
		return beans.size();
	}
	@Override
	public Sender getItem(int position) {
		if(beans==null)return null;
		return beans.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	public void addData(List<Sender> list) {
		if(list != null){
			beans.addAll(list);
			notifyDataSetChanged();
		}
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;  
		if(convertView != null){
			holder=(ViewHolder)convertView.getTag();
		}else{
			holder=new ViewHolder();
			convertView = inflater.inflate( R.layout.item_sender_sms, null);
			holder.userName=(TextView) convertView.findViewById(R.id.userName); 
			holder.userContent=(TextView) convertView.findViewById(R.id.userContent); 
			holder.userIcon=(ImageView) convertView.findViewById(R.id.userIcon); 
			convertView.setTag(holder);
		}
		if(beans == null || beans.size() == 0 ) return convertView;
		Sender Sender = beans.get(position);
		holder.userName.setText(Sender.getSender_nick());
		holder.userContent.setText(Sender.getText());
		ImageLoader.getInstance().displayImage(Sender.getSender_icon(),holder.userIcon);
		return convertView;
	}
	
	
	
	 class ViewHolder{  
            TextView userName;  
            TextView userContent;  
            ImageView userIcon;
        }  
}