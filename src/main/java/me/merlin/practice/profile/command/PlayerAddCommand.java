package me.merlin.practice.profile.command;

import me.merlin.practice.Practice;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerAddCommand implements CommandExecutor {


    // /add command
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("practice.player.add")) {
                if(strings.length == 0) {
                    if(Practice.getInstance().getMongoHandler().readPlayer(player.getUniqueId()) == null) {
                        Practice.getInstance().getMongoHandler().storePlayer(player);
                        player.sendMessage("§aYou have been added to the database.");

                    } else {
                        player.sendMessage("§cYou already have a profile!");
                    }
                }

                if(strings.length == 1) {
                    Player target = Bukkit.getPlayerExact(strings[0]);
                    if(target != null) {
                        if(Practice.getInstance().getMongoHandler().readPlayer(target.getUniqueId()) == null) {
                            Practice.getInstance().getMongoHandler().storePlayer(target);
                            player.sendMessage("§aAdded player §e" + target.getName() + "§a's profile!");
                        }
                    }
                }

                if(strings.length > 1) {
                    player.sendMessage("§cUsage: /add <player> OR /add");
                }
            } else {
                player.sendMessage("§cYou do not have permission to use this command!");
            }

        }

        return false;
    }
}
