package me.merlin.practice.profile;

import me.merlin.practice.Practice;
import me.merlin.practice.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerHandler {

    public PlayerHandler() {

        Practice.getInstance().getServer().getPluginManager().registerEvents(new PlayerListener(), Practice.getInstance());
    }


    public void resetPlayer(Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }

        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        PlayerProfile profile = profileHandler.getProfile(player);

        player.setGameMode(GameMode.SURVIVAL);

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.updateInventory();

        player.getActivePotionEffects().clear();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setExhaustion(0);
        player.setSaturation(5);
        player.setLevel(0);
        player.setExp(0);
        player.setFireTicks(0);

        player.getActivePotionEffects().forEach(p -> player.removePotionEffect(p.getType()));

    }

    public void teleportSpawn(Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }

        Location spawn = new Location(player.getWorld(), 0, 0, 0);
        spawn.setX(Practice.getInstance().getConfig().getDouble("spawn.x"));
        spawn.setY(Practice.getInstance().getConfig().getDouble("spawn.y"));
        spawn.setZ(Practice.getInstance().getConfig().getDouble("spawn.z"));
        spawn.setYaw(180f);
        player.teleport(spawn);

        Bukkit.getOnlinePlayers().forEach(p -> {
            p.showPlayer(player);
            player.showPlayer(p);
        });


    }

    public void giveItems(Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }

        resetPlayer(player);

        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        PlayerProfile profile = profileHandler.getProfile(player);

        switch (profile.getPlayerState()) {
            case LOBBY:
                giveItem(Items.UNRANKED, player);
                giveItem(Items.RANKED, player);
                break;
            case QUEUE:
                giveItem(Items.LEAVE_QUEUE, player);
                break;
        }
        player.updateInventory();

    }

    private void giveItem(Items items, Player player) {
        if (items.isEnabled()) player.getInventory().setItem(items.getSlot(), items.getItem());
    }
}
