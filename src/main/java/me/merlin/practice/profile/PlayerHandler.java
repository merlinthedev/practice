package me.merlin.practice.profile;

import me.merlin.practice.Practice;
import me.merlin.practice.items.Items;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import sun.java2d.cmm.Profile;

public class PlayerHandler {


    public void resetPlayer(Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }

        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        PlayerProfile profile = profileHandler.getProfile(player);

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
        player.teleport(spawn);

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
