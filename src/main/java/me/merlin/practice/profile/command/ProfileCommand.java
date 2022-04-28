package me.merlin.practice.profile.command;

import me.merlin.practice.Practice;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProfileCommand implements CommandExecutor {




    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if(player.hasPermission("practice.profile")) {
            if(strings.length == 0) {
                player.sendMessage(Practice.getInstance().getMongoHandler().readPlayer(player.getUniqueId()));
                return true;
            }
            if(strings.length == 1) {
                Player target = Bukkit.getPlayerExact(strings[0]);
                if(target != null) {
                    player.sendMessage(target.getDisplayName() + Practice.getInstance().getMongoHandler().readPlayer(target.getUniqueId()));
                    return true;
                } else {
                    player.sendMessage("Player not found");
                }
            }

        }


        return false;
    }
}
