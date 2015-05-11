package com.tt.jiaoyou.bean;

public class PhotoBean {
	private int distance;
	private int user_id;
	private String url;//
	private String text;
	private String nick;
	private int photo_id;
	private int greeted_nums;
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public int getPhoto_id() {
		return photo_id;
	}
	public void setPhoto_id(int photo_id) {
		this.photo_id = photo_id;
	}
	public int getGreeted_nums() {
		return greeted_nums;
	}
	public void setGreeted_nums(int greeted_nums) {
		this.greeted_nums = greeted_nums;
	}

}
