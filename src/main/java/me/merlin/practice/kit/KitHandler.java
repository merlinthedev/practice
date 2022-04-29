package me.merlin.practice.kit;

import me.merlin.practice.Practice;
import me.merlin.practice.kit.command.KitCommand;

public class KitHandler {

    public KitHandler() {
        Practice.getInstance().getCommand("kit").setExecutor(new KitCommand());
    }
}
