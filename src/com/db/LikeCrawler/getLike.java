package com.db.LikeCrawler;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class getLike {
	private final static String host = "10.108.192.165";
	private final static String dbName = "AlexYoung";
	private final static String username = "ittcdb";
	private final static String password = "ittc706706";
	private final static String collectionName = "truth";
	private static DBCollection commentCollection;

	public getLike() throws UnknownHostException {
		this.commentCollection = MongoConnection.getConnection(host, dbName,
				username, password, collectionName);
	}

	public void updateToDB(String name, int like) {
		DBObject updateSetValue = new BasicDBObject("$set", new BasicDBObject("Like",like));
		commentCollection.update(new BasicDBObject().append("ReviewerName", name), updateSetValue, false, false);
	}
}

