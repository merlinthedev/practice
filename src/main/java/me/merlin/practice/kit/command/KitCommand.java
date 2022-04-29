package me.merlin.practice.kit.command;

import me.merlin.practice.Practice;
import me.merlin.practice.util.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {

    private final Practice plugin = Practice.getInstance();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("practice.kit")) {
                
            }

        } else {
            Logger.error("You must be a player to use this command!");
        }

        return false;
    }
}
