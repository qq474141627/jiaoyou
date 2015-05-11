package com.tt.jiaoyou.xml;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.tt.jiaoyou.bean.Comment;
import com.tt.jiaoyou.bean.PhotoBean;
import com.tt.jiaoyou.bean.PhotoUserInfo;
import com.tt.jiaoyou.bean.Sender;
import com.tt.jiaoyou.bean.VipBean;
import com.tt.jiaoyou.bean.YueUserInfo;
import com.tt.jiaoyou.bean.VideoBean;
import com.tt.jiaoyou.ui.OPlayerApplication;
import com.tt.jiao.you.R;

public class XMLParser {
	private static String cur_user = "cur_user=1141049";
	
	public static ArrayList<VideoBean> getVideoBeans(){
		ArrayList<VideoBean> list=new ArrayList<VideoBean>();
		try {
			String url=XMLUtil.readJsonFromUrl("http://api.347.cc/video?"+cur_user);
			JSONObject obj=new JSONObject(url);
			JSONArray array=obj.optJSONArray("uploads");
			for(int i=0;i<array.length();i++){
				obj=array.optJSONObject(i);
				VideoBean bean=new VideoBean();
				bean.setId(obj.optInt("id"));
				bean.setImg(obj.optString("img"));
				bean.setVideo_url(obj.optString("video_url"));
				bean.setUser_id(obj.optInt("user_id"));
				bean.setText(obj.optString("text"));
				bean.setNick(obj.optString("nick"));
				if(i < 9){
					list.add(bean);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static ArrayList<PhotoBean> getPhotoBeans(String gender,int page){
		ArrayList<PhotoBean> list=new ArrayList<PhotoBean>();
		try {
			String url=XMLUtil.readJsonFromUrl("http://api.347.cc/photo?"+cur_user+"&gender="+gender+"&page="+page);
			JSONObject obj=new JSONObject(url);
			JSONArray array=obj.optJSONArray("photos");
			for(int i=0;i<array.length();i++){
				obj=array.optJSONObject(i);
				PhotoBean bean=new PhotoBean();
				bean.setDistance(obj.optInt("distance"));
				bean.setUrl(obj.optString("url"));
				bean.setPhoto_id(obj.optInt("photo_id"));
				bean.setUser_id(obj.optInt("user_id"));
				bean.setGreeted_nums(obj.optInt("greeted_nums"));
				bean.setText(obj.optString("text"));
				bean.setNick(obj.optString("nick"));
				list.add(bean);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static Comment getComment(int user_id,int photo_id){
		try {
			String url=XMLUtil.readJsonFromUrl("http://api.347.cc/photo/detail?"+cur_user+"&user_id="+user_id+"&photo_id="+photo_id);
			Comment comment = new Comment();
			JSONObject obj=new JSONObject(url);
			comment.setComment_nums(obj.optInt("comment_nums"));
			comment.setGreet_nums(obj.optInt("greet_nums"));
			comment.setIcon(obj.optString("icon"));
			
			ArrayList<String> photos=new ArrayList<String>();
			JSONArray photoArray=obj.optJSONArray("photo");
			for(int i=0;i<photoArray.length();i++){
				photos.add(photoArray.optString(i));
			}
			comment.setPhoto(photos);
			
			ArrayList<Sender> list=new ArrayList<Sender>();
			JSONArray sendArray=obj.optJSONArray("comments");
			for(int i=0;i<sendArray.length();i++){
				obj=sendArray.optJSONObject(i);
				Sender bean=new Sender();
				bean.setSender(obj.optString("sender"));
				bean.setSender_icon(obj.optString("sender_icon"));
				bean.setSender_nick(obj.optString("sender_nick"));
				bean.setText(obj.optString("text"));
				list.add(bean);
			}
			comment.setList(list);
			return comment;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static YueUserInfo getYueUserInfo(){
		try {
			String url=XMLUtil.readJsonFromUrl("http://api.347.cc/show?"+cur_user+"&gender=1");
			YueUserInfo info = new YueUserInfo();
			JSONObject obj=new JSONObject(url);
			info.setAge(obj.optString("age"));
			info.setCity(obj.optString("city"));
			info.setGreeted_nums(obj.optInt("greet_nums"));
			info.setHeight(obj.optString("height"));
			info.setNick(obj.optString("nick"));
			info.setPhoto_id(obj.optInt("photo_id"));
			info.setPhoto_nums(obj.optInt("photo_nums"));
			info.setUrl(obj.optString("url"));
			info.setUser_id(obj.optInt("user_id"));
			return info;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static PhotoUserInfo getPhotoUserInfo(int user_id){
		try {
			String url = XMLUtil.readJsonFromUrl("http://api.347.cc/user/v2?"+cur_user+"&user_id="+user_id);
			PhotoUserInfo info = new PhotoUserInfo();
			JSONObject obj = new JSONObject(url);
			info.setAge(obj.optString("age"));
			info.setCity(obj.optString("city"));
			info.setDistance(obj.optInt("distance"));
			info.setIntro(obj.optString("intro"));
			info.setNick(obj.optString("nick"));
			info.setGender(obj.optInt("gender"));
			info.setIs_focus(obj.optInt("is_focus"));
			info.setIs_vip(obj.optInt("is_vip"));
			info.setLast_login(obj.optString("last_login"));
			List<String> photolist = new ArrayList<String>();
			JSONArray array=obj.optJSONArray("photo");
			for(int i=0;i<array.length();i++){
				photolist.add(array.optString(i));
			}
			info.setPhoto(photolist);
			List<String> showslist = new ArrayList<String>();
			array=obj.optJSONArray("shows");
			for(int i=0;i<array.length();i++){
				showslist.add(array.optString(i));
			}
			info.setShows(showslist);
			List<String> videolist = new ArrayList<String>();
			array=obj.optJSONArray("video");
			for(int i=0;i<array.length();i++){
				videolist.add(array.optString(i));
			}
			info.setShows(videolist);
			return info;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static List<VipBean> getVipInfo(){
		try {
			String url = XMLUtil.readJsonFromUrl("HTTP://api.347.cc/pay?product_type=1&"+cur_user);
			JSONObject obj = new JSONObject(url);
			List<VipBean> list = new ArrayList<VipBean>();
			JSONArray array=obj.optJSONArray("products");
			for(int i=0;i<array.length();i++){
				obj = array.optJSONObject(i);
				VipBean bean = new VipBean();
				bean.setId(obj.optInt("id"));
				bean.setLeft(obj.optString("left"));
				bean.setName(obj.optString("name"));
				bean.setRmb(obj.optInt("rmb"));
				bean.setText(obj.optString("text"));
				list.add(bean);
			}
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
