package me.merlin.practice.spawn.command;


import me.merlin.practice.Practice;
import me.merlin.practice.util.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private Practice plugin;

    public SpawnCommand() {
        this.plugin = Practice.getInstance();
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            // Get a location from the config
            if (strings.length == 0) {
                Logger.info("You must specify a location to spawn at.");
                Location loc = new Location(player.getWorld(), plugin.getConfig().getDouble("spawn.x"), plugin.getConfig().getDouble("spawn.y"), plugin.getConfig().getDouble("spawn.z"));
                loc.setY(loc.getY() + 1);
                loc.setYaw(180f);
                player.teleport(loc);
                player.sendMessage(ChatColor.GREEN + "You have been teleported to the spawn.");


            }

            if (strings.length == 1) {
                if (strings[0].equalsIgnoreCase("set")) {
                    Location location = player.getLocation();
                    plugin.getConfig().set("spawn.x", location.getX());
                    plugin.getConfig().set("spawn.y", location.getY());
                    plugin.getConfig().set("spawn.z", location.getZ());


                    plugin.saveConfig();
                    player.sendMessage("§aSpawn set!");
                }
            }

        }
        return false;
    }
}
