package me.merlin.practice.duel.commands;

import com.google.common.collect.ImmutableList;
import com.mongodb.annotations.Immutable;
import me.merlin.practice.Practice;
import me.merlin.practice.duel.DuelRequest;
import me.merlin.practice.match.Match;
import me.merlin.practice.profile.PlayerProfile;
import me.merlin.practice.profile.ProfileHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (strings.length < 1) {
                player.sendMessage("§cUsage: /accept <player>");
            }
            Player target = Bukkit.getPlayerExact(strings[0]);
            if (target == null) {
                player.sendMessage("§cThat player is not online!");
                return true;
            }
            ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
            PlayerProfile playerProfile = profileHandler.getProfile(player);
            PlayerProfile targetProfile = profileHandler.getProfile(target);

            if (playerProfile.getPlayerState() != PlayerProfile.PlayerState.LOBBY) {
                player.sendMessage("§cYou are not in a lobby!");
                return true;
            }
            if (targetProfile.getPlayerState() != PlayerProfile.PlayerState.LOBBY) {
                player.sendMessage("§cThat player is not in a lobby!");
                return true;
            }

            DuelRequest duelRequest = playerProfile.getDuelRequests().stream().filter(d -> d.getSender().equals(target.getUniqueId()) && System.currentTimeMillis() - d.getTimestamp() < 30000).findFirst().orElse(null);
            if (duelRequest == null) {
                player.sendMessage("§cThat player has not sent you a duel request!");
                return true;
            }

            Match match = new Match(duelRequest.getKit(), ImmutableList.of(player.getUniqueId()), ImmutableList.of(target.getUniqueId()));



            // TODO give items to players
            playerProfile.setMatch(match);
            playerProfile.setPlayerState(PlayerProfile.PlayerState.MATCH);
            match.getKit().getUnrankedMatch().add(player.getUniqueId());

            targetProfile.setMatch(match);
            targetProfile.setPlayerState(PlayerProfile.PlayerState.MATCH);
            match.getKit().getUnrankedMatch().add(target.getUniqueId());


            match.start();
            playerProfile.getDuelRequests().remove(duelRequest);
        }

        return false;
    }
}
