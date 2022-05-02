package me.merlin.practice.duel;

import me.merlin.practice.Practice;
import me.merlin.practice.kit.Kit;
import me.merlin.practice.kit.KitHandler;
import me.merlin.practice.profile.PlayerProfile;
import me.merlin.practice.profile.ProfileHandler;
import me.merlin.practice.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DuelMenu implements Listener {

    private Practice plugin;
    private Inventory inv;
    private Player player;
    private Player target;

    public DuelMenu(Player player, Player target) {
        this.plugin = Practice.getInstance();
        this.player = player;
        this.target = target;
    }

    public DuelMenu() {
        player = null;
        target = null;
    }

    public void openMenu() {
//        kits.addAll(plugin.getConfig().getConfigurationSection("kits").getKeys(false));
//        inv = Bukkit.createInventory(player, kits.size() + (9 - (kits.size() % 9)), ChatColor.DARK_RED + "Choose a kit");
//        for (String kit : kits) {
//            ItemStack item = new ItemStack(plugin.getConfig().getInt("kits." + kit + ".item"));
//            ItemMeta meta = item.getItemMeta();
//            meta.setDisplayName(plugin.getConfig().getString("kits." + kit + ".name"));
//            item.setItemMeta(meta);
//            inv.addItem(item);
//        }
        inv = Bukkit.createInventory(player, Practice.getInstance().getKitHandler().getKitList().size() + (9 - (Practice.getInstance().getKitHandler().getKitList().size() % 9)), ChatColor.DARK_RED + "Choose a kit");
        KitHandler kitHandler = Practice.getInstance().getKitHandler();

        for(int i = 0; i < kitHandler.getKitList().size(); i++) {
            Kit kit = kitHandler.getKitList().get(i);

        }

        player.openInventory(inv);

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ProfileHandler handler = plugin.getProfileHandler();
        PlayerProfile profile = handler.getProfile(player);
        PlayerProfile targetProfile = handler.getProfile(target);
        if (event.getInventory().getTitle().equals(ChatColor.DARK_RED + "Choose a kit")) {
            // Check what item is clicked and check what kit it is
            ItemStack item = event.getCurrentItem();
            targetProfile.getDuelRequests().add(new DuelRequest(player.getUniqueId(), ));


            event.setCancelled(true);
        }
        if (event.getClickedInventory().getName() == null) {
            Logger.warning("Clicked inventory is null");
        }
    }

}
