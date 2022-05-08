package me.merlin.practice.items;

import me.merlin.practice.Practice;
import me.merlin.practice.util.Logger;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemHandler {

    public ItemHandler() {
        load();
    }

    private void load() {
        Arrays.stream(Items.values()).forEach(item -> {
            ItemStack itemStack = new ItemStack(Practice.getInstance().getConfig().getInt("items." + item.toString() + ".item"));
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(Practice.getInstance().getConfig().getString("items." + item.toString() + ".name"));
            itemStack.setItemMeta(itemMeta);
            item.setItem(itemStack);
            item.setSlot(Practice.getInstance().getConfig().getInt("items." + item + ".slot"));
            item.setEnabled(Practice.getInstance().getConfig().getBoolean("items." + item + ".enabled"));
        });
    }
}
