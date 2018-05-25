package com.myntra.apiTests.inbound.helper;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDBClient {
	private static final String MONGO_HOSTNAME = "scm-backend2";
	private static final String MONGO_DB = "prodDB";
	private static final String COLLECTION_NAME = "style_aggregates";

	public long get_mongo_db_count() throws UnknownHostException {

		MongoClient mongo = new MongoClient(MONGO_HOSTNAME, 37017);

		System.out.println("**************");
		List<String> dbs = mongo.getDatabaseNames();
		System.out.println(dbs);

		// Now connect to your databases
		DB db = mongo.getDB(MONGO_DB);

		long count = db.getCollection(COLLECTION_NAME).count();

		return count;

	}

}
