package me.merlin.practice;

import lombok.Getter;
import me.merlin.practice.config.ConfigHandler;
import me.merlin.practice.duel.DuelHandler;
import me.merlin.practice.effects.EffectHandler;
import me.merlin.practice.items.ItemHandler;
import me.merlin.practice.kit.KitHandler;
import me.merlin.practice.match.MatchHandler;
import me.merlin.practice.menu.MenuHandler;
import me.merlin.practice.mongo.MongoHandler;
import me.merlin.practice.profile.PlayerHandler;
import me.merlin.practice.profile.ProfileHandler;
import me.merlin.practice.spawn.SpawnHandler;
import me.merlin.practice.util.BuilderCommand;
import me.merlin.practice.util.DebugCommands;
import me.merlin.practice.util.DebugHandler;
import me.merlin.practice.util.Logger;
import org.bukkit.Effect;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Practice extends JavaPlugin {

    //ToDo: Add multiple config files; kits, config.yml, arenas

    @Getter public static Practice instance;

    @Getter private MongoHandler mongoHandler;
    @Getter private ProfileHandler profileHandler;
    @Getter private MatchHandler matchHandler;
    @Getter private KitHandler kitHandler;
    @Getter private DuelHandler duelHandler;
    @Getter private SpawnHandler spawnHandler;
    @Getter private MenuHandler menuHandler;
    @Getter private PlayerHandler playerHandler;
    @Getter private EffectHandler effectHandler;
    @Getter private ItemHandler itemHandler;
    @Getter private DebugHandler debugHandler;
    @Getter private ConfigHandler configHandler;


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
        configHandler = new ConfigHandler();
        profileHandler = new ProfileHandler();
        kitHandler = new KitHandler();
        matchHandler = new MatchHandler();
        duelHandler = new DuelHandler();
        spawnHandler = new SpawnHandler();
        effectHandler = new EffectHandler();
        menuHandler = new MenuHandler();
        itemHandler = new ItemHandler();
        playerHandler = new PlayerHandler();
        debugHandler = new DebugHandler();
    }

    private void disableGameRules(String... gameRules) {
        Arrays.stream(gameRules).forEach(gr -> this.getServer().getWorlds().stream().forEach(w -> w.setGameRuleValue(gr, "false")));
    }

}
