package me.merlin.practice.duel;

import me.merlin.practice.Practice;
import me.merlin.practice.duel.commands.AcceptCommand;
import me.merlin.practice.duel.commands.DuelCommand;

public class DuelHandler {

    private Practice plugin;

    public DuelHandler() {
        plugin = Practice.getInstance();

        plugin.getCommand("duel").setExecutor(new DuelCommand());
        plugin.getCommand("accept").setExecutor(new AcceptCommand());
    }
}
