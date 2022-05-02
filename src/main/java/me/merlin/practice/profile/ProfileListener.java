package me.merlin.practice.profile;

import me.merlin.practice.Practice;
import me.merlin.practice.mongo.MongoHandler;
import me.merlin.practice.util.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ProfileListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        MongoHandler mongoHandler = Practice.getInstance().getMongoHandler();

        if(!profileHandler.hasProfile(player)) {
            profileHandler.addPlayer(player);
        }

        PlayerProfile profile = profileHandler.getProfile(player);
        profile.setPlayerState(PlayerProfile.PlayerState.LOBBY);




        if(!mongoHandler.exists(player.getUniqueId())) {
            mongoHandler.storePlayer(player);
        } else {
            Logger.info("Player " + player.getName() + " already exists in the database.");
        }
    }

    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        profileHandler.removeProfile(player);


    }
}
