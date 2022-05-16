package me.merlin.practice.util;

import me.merlin.practice.Practice;
import me.merlin.practice.profile.PlayerHandler;
import me.merlin.practice.profile.PlayerProfile;
import me.merlin.practice.profile.ProfileHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveItemsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("practice.giveitems")) {
                if(strings.length == 0) {
                    PlayerHandler playerHandler = Practice.getInstance().getPlayerHandler();
                    playerHandler.giveItems(player);
                    return true;
                } else if(strings.length == 1) {
                    Player target = Bukkit.getPlayerExact(strings[0]);
                    if(target == null) {
                        Logger.error("Player not found!");
                        player.sendMessage("§cPlayer not found!");
                        return true;
                    }

                    PlayerHandler playerHandler = Practice.getInstance().getPlayerHandler();
                    playerHandler.giveItems(target);
                    return true;
                } else {
                    player.sendMessage("§cUsage: /giveitems [player]");
                }

            }

        }
        return false;
    }
}
