package me.merlin.practice.spawn;

import me.merlin.practice.Practice;
import me.merlin.practice.spawn.command.SpawnCommand;

public class SpawnHandler {

    private Practice plugin;

    public SpawnHandler() {

        plugin = Practice.getInstance();
        plugin.getCommand("spawn").setExecutor(new SpawnCommand());



    }
}
