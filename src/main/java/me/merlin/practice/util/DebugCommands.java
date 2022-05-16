package me.merlin.practice.util;

import me.merlin.practice.Practice;
import me.merlin.practice.profile.PlayerProfile;
import me.merlin.practice.profile.ProfileHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("state")) {
            ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
            if(commandSender instanceof Player) {
                Player player = (Player) commandSender;
                PlayerProfile playerProfile = profileHandler.getProfile(player);
                player.sendMessage(playerProfile.getPlayerState().toString());
                return true;
            }
        }
        return false;
    }
}
