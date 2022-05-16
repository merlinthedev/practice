package me.merlin.practice.match;

import me.merlin.practice.Practice;
import me.merlin.practice.kit.Kit;
import me.merlin.practice.profile.PlayerProfile;
import me.merlin.practice.profile.ProfileHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MatchListener implements Listener {

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        MatchHandler matchHandler = Practice.getInstance().getMatchHandler();

        PlayerProfile profile = profileHandler.getProfile(player);
        if (matchHandler.getMatch(player) == null) {
            event.setCancelled(true);
            return;
        }

        Match match = matchHandler.getMatch(player);
        if (match.getDead().contains(player.getUniqueId())) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        MatchHandler matchHandler = Practice.getInstance().getMatchHandler();

        PlayerProfile profile = profileHandler.getProfile(player);
        if (matchHandler.getMatch(player) == null) {
            event.setCancelled(true);
            return;
        }

        Match match = matchHandler.getMatch(player);
        if (match.getDead().contains(player.getUniqueId())) {
            event.setCancelled(true);
            return;
        }

        match.getItems().add(event.getItemDrop());
        Bukkit.getScheduler().scheduleSyncDelayedTask(Practice.getInstance(), () -> event.getItemDrop().remove(), 100L);

    }

    @EventHandler
    public void onHungerEvent(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();

        MatchHandler matchHandler = Practice.getInstance().getMatchHandler();
        if (matchHandler.getMatch(player) == null) {
            event.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);

        Player player = event.getEntity();

        MatchHandler matchHandler = Practice.getInstance().getMatchHandler();
        if (matchHandler.getMatch(player) != null) {
            Match match = matchHandler.getMatch(player);

            if (((match.getAlive(match.getTeamOne().contains(player.getUniqueId())) > 1))) {
                event.getDrops().forEach(i -> match.getItems().add(player.getLocation().getWorld().dropItem(player.getLocation(), i)));
            }

            event.getDrops().clear();

            match.addDeath(player, player.getKiller() == null ? Match.MatchDeathReason.DIED : Match.MatchDeathReason.KILLED, player.getKiller());
            player.setHealth(20);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        MatchHandler matchHandler = Practice.getInstance().getMatchHandler();
        if (matchHandler.getMatch(player) != null) {
            Match match = matchHandler.getMatch(player);
            // TODO: UHC
        }


        // TODO: BUILDER
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        MatchHandler matchHandler = Practice.getInstance().getMatchHandler();
        Player player = (Player) event.getEntity();

        if (matchHandler.getMatch(player) == null) {
            event.setCancelled(true);
            return;
        }

        Match match = matchHandler.getMatch(player);
        if (match.getMatchState() != Match.MatchState.ACTIVE) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        MatchHandler matchHandler = Practice.getInstance().getMatchHandler();
        Player player = (Player) event.getEntity();

        if (matchHandler.getMatch(player) == null) {
            event.setCancelled(true);
            return;
        }

        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();

            if (matchHandler.getMatch(damager) == null) {
                event.setCancelled(true);
                return;
            }

            Match match = matchHandler.getMatch(damager);

            if (match.getMatchState() != Match.MatchState.ACTIVE) {
                event.setCancelled(true);
                return;
            }

            Kit kit = match.getKit();
            if (kit != null && !kit.isDamage()) {
                event.setDamage(0);
                return;
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        MatchHandler matchHandler = Practice.getInstance().getMatchHandler();

        if (matchHandler.getMatch(player) != null) {
            Match match = matchHandler.getMatch(player);

            match.addDeath(player, Match.MatchDeathReason.DISCONNECTED, null);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        PlayerProfile profile = profileHandler.getProfile(player);


        if (profile.getPlayerState() != PlayerProfile.PlayerState.MATCH) {
            if (!profile.isBuilder()) {
                event.setCancelled(true);
            }
            return;
        }
    }
}
