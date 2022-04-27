package me.merlin.practice;

import lombok.Getter;
import lombok.Setter;
import me.merlin.practice.mongo.MongoHandler;
import me.merlin.practice.profile.ProfileHandler;
import me.merlin.practice.util.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Practice extends JavaPlugin {

    @Getter
    public static Practice instance;

    @Getter
    private MongoHandler mongoHandler;

    @Getter
    private ProfileHandler profileHandler;


    public void onEnable() {

        System.out.println(":D");
        System.out.println("Merlin branch");
        System.out.println("OUR BRANCH");

        registerHandlers();

        disableGameRules(
                "doDaylightCycle",
                "doMobSpawning"
        );
        Logger.success("Practice has been enabled!");
    }

    public void onDisable() {
        Logger.success("Practice has been disabled!");
    }


    private void registerHandlers() {
        mongoHandler = new MongoHandler();
        profileHandler = new ProfileHandler();

    }

    private void disableGameRules(String... gameRules) {
        Arrays.stream(gameRules).forEach(gr -> this.getServer().getWorlds().stream().forEach(w -> w.setGameRuleValue(gr, "false")));
    }

}
