package com.db.LikeCrawler;


public class Lancer {
	public static void main(String[] args) throws Exception{
		CrawLike cLike = new CrawLike();
		//page	weibo_MID	author_UID
		cLike.getPage(54, "BBZ15CR1B", "1252397723");
	}
}
