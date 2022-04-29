package me.merlin.practice;

import lombok.Getter;
import me.merlin.practice.kit.KitHandler;
import me.merlin.practice.match.MatchHandler;
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

    @Getter
    private MatchHandler matchHandler;

    @Getter
    private KitHandler kitHandler;


    public void onEnable() {
        instance = this;

        registerHandlers();


        // register config.yml file
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

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
        kitHandler = new KitHandler();
        matchHandler = new MatchHandler();
    }

    private void disableGameRules(String... gameRules) {
        Arrays.stream(gameRules).forEach(gr -> this.getServer().getWorlds().stream().forEach(w -> w.setGameRuleValue(gr, "false")));
    }

}
