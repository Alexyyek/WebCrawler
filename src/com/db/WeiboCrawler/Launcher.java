package com.db.WeiboCrawler;

public class Launcher {
	
	public static void main(String[] args) throws Exception{
		getMongoStatus gStatus = new getMongoStatus();
		gStatus.getUid();
	}
}
