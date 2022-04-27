package me.merlin.practice.profile;

import me.merlin.practice.Practice;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ProfileListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();

        if(!profileHandler.hasProfile(player)) {
            profileHandler.addPlayer(player);
        }
    }

    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        profileHandler.removeProfile(player);


    }
}
