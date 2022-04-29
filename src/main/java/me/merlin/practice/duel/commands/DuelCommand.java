package me.merlin.practice.duel.commands;

import me.merlin.practice.Practice;
import me.merlin.practice.duel.DuelRequest;
import me.merlin.practice.profile.PlayerProfile;
import me.merlin.practice.profile.ProfileHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (strings.length < 1) {
                player.sendMessage("§cUsage: /duel <player>");
                return true;
            }
            Player target = Bukkit.getPlayerExact(strings[0]);
            if (target == null) {
                player.sendMessage("§cPlayer not found.");
                return true;
            }
            ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
            PlayerProfile playerProfile = profileHandler.getProfile(player);
            PlayerProfile targetProfile = profileHandler.getProfile(target);
            if (playerProfile.getPlayerState() != PlayerProfile.PlayerState.LOBBY) {
                player.sendMessage("§cYou must be in the lobby to duel.");
                return true;
            }

            if (targetProfile.getPlayerState() != PlayerProfile.PlayerState.LOBBY) {
                player.sendMessage("§cThat player is not in the lobby.");
                return true;
            }

            DuelRequest duelRequest = targetProfile.getDuelRequests().stream().filter(d ->
            d.getSender().equals(player.getUniqueId())
            && System.currentTimeMillis() - d.getTimestamp() <= 30000).findFirst().orElse(null);
            if(duelRequest != null) {
                player.sendMessage("§cYou already sent a duel request to that player.");
                return true;
            }

            targetProfile.getDuelRequests().add(new DuelRequest(player.getUniqueId(), ));
            player.sendMessage("§aYou sent a duel request to §e" + target.getName() + "§a.");

        }

        return false;
    }
}
