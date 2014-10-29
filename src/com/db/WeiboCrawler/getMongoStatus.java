package com.db.WeiboCrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import net.sf.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class getMongoStatus {
	private final static String host = "10.108.192.165";
	private final static String dbName = "AlexYoung";
	private final static String username = "ittcdb";
	private final static String password = "ittc706706";
	private final static String collectionName = "VipUserStatus";
	private final static String collectionStuff = "VipUserComment";
	private static DBCollection commentCollection;
	private static DBCollection statusCollection;
	private String path = "D:/Workspace/weibo4j-oauth2-user/data/test.txt";

	public getMongoStatus() throws UnknownHostException {
		this.statusCollection = MongoConnection.getConnection(host, dbName,
				username, password, collectionName);
		this.commentCollection = MongoConnection.getConnection(host, dbName,
				username, password, collectionStuff);
	}

	public void getStatusMid(String Uid) throws IOException, Exception {
		DBObject dbObject = new BasicDBObject();
		DBObject query = new BasicDBObject();
		query.put("UserID", 1314637182);
		dbObject.put("WeiboMid", true);
		dbObject.put("Comment_Count", true);
		dbObject.put("Text", true);
		DBCursor cursor = statusCollection.find(query, dbObject);
		//SinaCrawler sCrawler = new SinaCrawler();
		//sCrawler.getSeed("1314637182", "BnBTKuClI", "昨天维也纳地铁逮到了第一百万个逃票的人，为了庆祝，送给这个人10万欧元", 10000);
		while(cursor.hasNext()){
			String info = cursor.next().toString();
			JSONObject object = JSONObject.fromObject(info);
			String WeiboMid = object.optString("WeiboMid");
			int Comment_Count = object.optInt("Comment_Count");
			String text = object.optString("Text");
			SinaCrawler sCrawler = new SinaCrawler();
			sCrawler.getSeed(Uid, WeiboMid, text, Comment_Count);
			System.out.println(WeiboMid);
		}
	}

	public void getUid() throws Exception {
		InputStreamReader iReader = new InputStreamReader(new FileInputStream(
				new File(path)));
		BufferedReader bReader = new BufferedReader(iReader);
		String uid;
		while ((uid = bReader.readLine()) != null) {
			getStatusMid(uid);
		}
		bReader.close();
	}

	public void updateToDB(String name, ReviewerData rData) {
		BasicDBObject query = new BasicDBObject("Name",name);
		DBObject object = new BasicDBObject();
		object.put("Uid", rData.getUid());
		object.put("Mid", rData.getMid());
		object.put("Text", rData.getText());
		object.put("Name", rData.getName());
		object.put("Comment", rData.getComment());
		object.put("Like", rData.getLike());
		object.put("ReviewTime", rData.getTime());
		DBObject updateSetValue = new BasicDBObject("$set", object);
		commentCollection.update(query, updateSetValue, true, false);
	}
}
