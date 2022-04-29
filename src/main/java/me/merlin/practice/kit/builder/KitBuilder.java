package me.merlin.practice.kit.builder;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitBuilder {

    public static Inventory build(String name, int size, Player owner, ItemStack[] items) {
        Inventory inventory = Bukkit.createInventory(owner, size, name);
        inventory.setContents(items);
        return inventory;
    }
}
