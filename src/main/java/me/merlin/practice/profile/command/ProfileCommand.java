package me.merlin.practice.profile.command;

import me.merlin.practice.Practice;
import me.merlin.practice.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProfileCommand implements CommandExecutor {

    Practice plugin;

    public ProfileCommand() {
        plugin = Practice.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("practice.profile")) {
                if (strings.length == 0) {
                    if(plugin.getMongoHandler().exists(player.getUniqueId())) {
                        Logger.success("&7Your profile is &a&l✔");
//                        String output = plugin.getMongoHandler().readPlayer(player.getUniqueId());
//                        Logger.info(output);
                        player.sendMessage(plugin.getMongoHandler().readPlayer(player.getUniqueId()));

                    } else {
                        player.sendMessage("§cPlayer does not exists!");
                        return true;
                    }
                }
                if (strings.length == 1) {
                    Player target = Bukkit.getPlayerExact(strings[0]);
                    if (target != null) {
                        player.sendMessage(target.getDisplayName() + Practice.getInstance().getMongoHandler().readPlayer(target.getUniqueId()));
                        return true;
                    } else {
                        player.sendMessage("Player not found");
                    }
                }

            }

        }
        return false;
    }
}
