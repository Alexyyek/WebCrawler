package com.db.WeiboCrawler;

import java.util.Date;

public class ReviewerData {
	private String name;
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	private String Comment;
	
	public String getComment(){
		return Comment;
	}
	
	public void setComment(String Comment){
		this.Comment = Comment;
	}
	
	private int like;
	
	public int getLike(){
		return like;
	}
	
	public void setLike(int like){
		this.like = like;
	}
	
	private Date time;
	
	public Date getTime(){
		return time;
	}
	
	public void setTime(Date time){
		this.time = time;
	}
	
	private String Uid;
	 
	public String getUid(){
		return Uid;
	}
	
	public void setUid(String Uid){
		this.Uid = Uid;
	}
	
	private String Mid;
	
	public String getMid(){
		return Mid;
	}
	
	public void setMid(String Mid){
		this.Mid = Mid;
	}
	
	private String text;
	
	public String getText(){
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	
}
