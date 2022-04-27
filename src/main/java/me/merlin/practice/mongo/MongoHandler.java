package me.merlin.practice.mongo;

import com.mongodb.*;
import lombok.Getter;
import me.merlin.practice.profile.PlayerProfile;
import me.merlin.practice.util.Logger;

import java.util.UUID;

public class MongoHandler {

    @Getter
    private MongoClient mongoClient;
    @Getter
    private DB mongoDatabase;
    @Getter
    private DBCollection collection;

    public MongoHandler() {
        try {
            String mongoUri = "mongodb://localhost:27017";
            MongoClientURI uri = new MongoClientURI(mongoUri, MongoClientOptions.builder().maxWaitTime(30000).maxConnectionIdleTime(5000).threadsAllowedToBlockForConnectionMultiplier(500));
            mongoClient = new MongoClient(uri);
            mongoDatabase = mongoClient.getDB("zenex");
            collection = mongoDatabase.getCollection("practice");

            mongoClient.getAddress();
            Logger.success("MongoDB connection established!");

        } catch (Exception e) {
            Logger.error("Failed to connect to MongoDB!");
            e.printStackTrace();
        }
    }



}
