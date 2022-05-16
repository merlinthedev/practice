package me.merlin.practice.profile;

import me.merlin.practice.Practice;
import me.merlin.practice.kit.KitHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerHandler playerHandler = Practice.getInstance().getPlayerHandler();
        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        KitHandler kitHandler = Practice.getInstance().getKitHandler();

        PlayerProfile practiceProfile = profileHandler.getProfile(player);

        playerHandler.resetPlayer(player);
        playerHandler.giveItems(player);
        playerHandler.teleportSpawn(player);

    }


}
