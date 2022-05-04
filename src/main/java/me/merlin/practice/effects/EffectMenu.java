package me.merlin.practice.effects;

import me.merlin.practice.Practice;
import me.merlin.practice.kit.Kit;
import me.merlin.practice.kit.KitHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Material.*;



public class EffectMenu implements Listener {

    private Practice plugin;
    private Inventory inv;
    private Player player;
    private Player target;

    public EffectMenu(Player player, Player target) {
        this.plugin = Practice.getInstance();
        this.player = player;
        this.target = target;
    }

    public EffectMenu() {
        player = null;
        target = null;
    }

    public void OpenMenu(){

        inv = Bukkit.createInventory(player, 9),"Choose an effect!");
        KitHandler kitHandler = Practice.getInstance().getKitHandler();


        for(int i = 0; i < optionIcons.length; i++) {
            if (optionIcons[i] != null) {
                Inventory.setItem(i, optionIcons[i]);
            }
        }

        player.openInventory(inv);

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        EffectMenu menu = new EffectMenu("Effect menu", 9, new EffectMenu.InventoryClickEventHandler() {
            public void onInventoryClick(EffectMenu.InventoryClickEvent event) {
                event.getPlayer().sendMessage("You have chosen" + event.Getname());
                event.setWillClose(true);
            }
        }, plugin)
                .setOption(3, new ItemStack(APPLE, 1), "Bruh Bruh Bruh test1", "BRUHHH")
                .setOption(4, new ItemStack(DIAMOND, 1), "Bruh Bruh Bruh test2", "BRUHHH2")
                .setOption(5, new ItemStack(EMERALD, 1), "Bruh Bruh Bruh test3", "BRUHHH3");
    }
}