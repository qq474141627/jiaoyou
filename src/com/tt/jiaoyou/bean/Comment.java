package com.tt.jiaoyou.bean;

import java.util.ArrayList;
import java.util.List;

public class Comment {

	private ArrayList<String> photo;//本人照片
	private int comment_nums;
	private int greet_nums;
	private String icon;//大图
	private ArrayList<Sender> list;
	public ArrayList<String> getPhoto() {
		return photo;
	}
	public void setPhoto(ArrayList<String> photo) {
		this.photo = photo;
	}
	public int getComment_nums() {
		return comment_nums;
	}
	public void setComment_nums(int comment_nums) {
		this.comment_nums = comment_nums;
	}
	public int getGreet_nums() {
		return greet_nums;
	}
	public void setGreet_nums(int greet_nums) {
		this.greet_nums = greet_nums;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public ArrayList<Sender> getList() {
		return list;
	}
	public void setList(ArrayList<Sender> list) {
		this.list = list;
	}
	
}
