package me.merlin.practice.util;

import me.merlin.practice.Practice;
import me.merlin.practice.profile.PlayerProfile;
import me.merlin.practice.profile.ProfileHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuilderCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
            PlayerProfile playerProfile = profileHandler.getProfile(player);

            if(strings.length != 0) {
                return false;
            }

            playerProfile.setBuilder(!playerProfile.isBuilder());
            player.sendMessage("§aYou are now " + (playerProfile.isBuilder() ? "§aBuilder" : "§cNot Builder"));
        }
        return false;
    }
}
