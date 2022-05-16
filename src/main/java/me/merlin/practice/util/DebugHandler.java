package me.merlin.practice.util;

import me.merlin.practice.Practice;

public class DebugHandler {

    private Practice practice;

    public DebugHandler() {
        practice = Practice.getInstance();
        practice.getCommand("state").setExecutor(new DebugCommands());
        practice.getCommand("builder").setExecutor(new BuilderCommand());
        practice.getCommand("giveitems").setExecutor(new GiveItemsCommand());
    }
}
