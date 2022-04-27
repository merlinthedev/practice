package me.merlin.practice.util;

import org.bukkit.Bukkit;

public class Logger {
    public static void success(String message) {
        Bukkit.getConsoleSender().sendMessage("§a[Practice] " + message);
    }

    public static void info(String message) {
        Bukkit.getConsoleSender().sendMessage("§b[Practice] " + message);
    }

    public static void warning(String message) {
        Bukkit.getConsoleSender().sendMessage("§e[Practice] " + message);
    }

    public static void error(String message) {
        Bukkit.getConsoleSender().sendMessage("§c[Practice] " + message);
    }
}
