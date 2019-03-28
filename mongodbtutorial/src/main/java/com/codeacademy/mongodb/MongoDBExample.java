package com.codeacademy.mongodb;

import com.codeacademy.mongodb.util.MongoDBUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBExample {

    public static void main(String[] theory) {

        MongoClient mongoClient = MongoDBUtil.createMongoClient();
        MongoDatabase database = mongoClient.getDatabase("tutorial");
        MongoCollection<Document> collection = database.getCollection("firstCollection");

        Document person = new Document("name", "Medardas")
                .append("skill", "MongoDB");

        collection.insertOne(person);

        System.out.println(collection.countDocuments());
    }

}
