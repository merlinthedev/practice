package me.merlin.practice.profile;

import me.merlin.practice.Practice;
import me.merlin.practice.mongo.MongoHandler;
import me.merlin.practice.util.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ProfileListener implements Listener {





    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        MongoHandler mongoHandler = Practice.getInstance().getMongoHandler();

        if (!profileHandler.hasProfile(player)) {
            profileHandler.addPlayer(player);
        }

        PlayerProfile profile = profileHandler.getProfile(player);
        profile.setPlayerState(PlayerProfile.PlayerState.LOBBY);


        if (!mongoHandler.exists(player.getUniqueId())) {
            mongoHandler.storePlayer(player);
        } else {
            Logger.info("Player " + player.getName() + " already exists in the database.");
        }
    }

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();
        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        PlayerProfile playerProfile = profileHandler.getProfile(player);
        if (playerProfile.getPlayerState() == PlayerProfile.PlayerState.LOBBY) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (profileHandler.getProfile(player).getPlayerState() == PlayerProfile.PlayerState.LOBBY) {
                event.setCancelled(true);
            }

        }
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        //TODO: Implement block break event for UHC mode
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        profileHandler.removeProfile(player);
    }
}
