package me.merlin.practice.menu;

import me.merlin.practice.Practice;
import org.bukkit.Bukkit;

public class MenuHandler {

    public MenuHandler() {

        Bukkit.getPluginManager().registerEvents(new MenuListener(), Practice.getInstance());
    }
}
