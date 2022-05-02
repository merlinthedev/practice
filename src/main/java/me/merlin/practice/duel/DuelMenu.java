package me.merlin.practice.duel;

import me.merlin.practice.Practice;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class DuelMenu implements Listener {

    private Practice plugin;
    private ArrayList<String> kits;
    private Inventory inv;
    private Player player;

    public DuelMenu(Player player) {
        this.plugin = Practice.getInstance();
        this.kits = new ArrayList<>();
        this.player = player;
    }

    public void openMenu() {
        kits.addAll(plugin.getConfig().getConfigurationSection("kits").getKeys(false));
        inv = Bukkit.createInventory(player, kits.size(), ChatColor.DARK_RED + "Choose a kit");
        for (String kit : kits) {
            ItemStack item = new ItemStack(plugin.getConfig().getInt("kits." + kit + ".item"));
            inv.addItem(item);
        }

        player.openInventory(inv);

    }

    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(event.getClickedInventory().getName().equals(ChatColor.DARK_RED + "Choose a kit"));
    }

}
