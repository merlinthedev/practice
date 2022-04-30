package me.merlin.practice.mongo;

import com.mongodb.*;
import lombok.Getter;
import me.merlin.practice.Practice;
import me.merlin.practice.kit.Kit;
import me.merlin.practice.profile.PlayerProfile;
import me.merlin.practice.profile.ProfileHandler;
import me.merlin.practice.util.Logger;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MongoHandler {

    @Getter
    private MongoClient mongoClient;
    @Getter
    private DB mongoDatabase;
    @Getter
    private DBCollection collection;

    public MongoHandler() {

        // Connect to the database
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


    // Store a player in the database
    public void storePlayer(Player player) {
        DBObject object = new BasicDBObject("uuid", player.getUniqueId());
        object.put("name", player.getName());
        object.put("uuid", player.getUniqueId());
        object.put("elo", 1000);
        collection.insert(object);
    }

    // Get a player from the database
    public String[] readPlayer(UUID uuid) {
        String[] output = new String[2];
        DBObject r = new BasicDBObject("uuid", uuid);
        DBObject result = collection.findOne(r);
        if (result == null) {
            Logger.warning("Could not find player with UUID: " + uuid);
            return null;
        }

        Logger.success("Found player with UUID: " + uuid);
        String name = (String) result.get("name");
        int elo = (int) result.get("elo");

        output[0] = name;
        output[1] = String.valueOf(elo);

        return output;
    }

    public boolean exists(UUID uuid) {
        boolean exists = false;
        DBObject query = new BasicDBObject("uuid", uuid);
        DBObject cursor = collection.findOne(query);
        if(cursor != null) {
            Logger.info("Player exists!");
            exists = true;
        } else {
            Logger.info("Player does not exist!");
        }

        return exists;

    }

}
