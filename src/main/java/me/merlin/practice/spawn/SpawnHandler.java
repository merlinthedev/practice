package me.merlin.practice.spawn;

import me.merlin.practice.Practice;
import me.merlin.practice.spawn.command.SpawnCommand;

public class SpawnHandler {

    Practice plugin;

    public SpawnHandler() {
        // TODO
        // Register Spawn Command
        // Constructor

        plugin = Practice.getInstance();
        plugin.getCommand("spawn").setExecutor(new SpawnCommand());



    }
}
