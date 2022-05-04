package me.merlin.practice.effects;

import me.merlin.practice.Practice;
import me.merlin.practice.kit.command.KitCommand;

public class EffectHandler {

    private Practice plugin;

    public EffectHandler() {
        plugin = Practice.getInstance();
        plugin.getCommand("effect").setExecutor(new EffectCommand());

    }
}
