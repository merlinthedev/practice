package me.merlin.practice.profile;

import me.merlin.practice.Practice;
import me.merlin.practice.mongo.MongoHandler;
import me.merlin.practice.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class ProfileListener implements Listener {





    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        MongoHandler mongoHandler = Practice.getInstance().getMongoHandler();
        PlayerHandler playerHandler = Practice.getInstance().getPlayerHandler();

        if (!profileHandler.hasProfile(player)) {
            profileHandler.addPlayer(player);
        }

        if (!mongoHandler.exists(player.getUniqueId())) {
            mongoHandler.storePlayer(player);
        } else {
            Logger.info("Player " + player.getName() + " already exists in the database.");
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        profileHandler.removeProfile(player);
    }




    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        //TODO: Implement block break event for UHC mode
        event.setCancelled(true);
    }


    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(profileHandler.getProfile(player) == null) {
                profileHandler.addPlayer(player);
                Logger.success("Added player " + player.getName() + " to the profile handler.");
            }

        }

    }
}
