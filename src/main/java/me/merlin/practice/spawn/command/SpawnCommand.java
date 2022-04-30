package me.merlin.practice.spawn.command;


import me.merlin.practice.Practice;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            // Get a location from the config
            if(strings.length == 0) {

            }

            if(strings.length == 1) {
                if(strings[0].equalsIgnoreCase("set")) {
                    Location location = player.getLocation();

                }
            }

        }
        return false;
    }
}
