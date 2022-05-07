package me.merlin.practice.items;

import me.merlin.practice.Practice;

import java.util.Arrays;

public class ItemHandler {

    public ItemHandler() {
        load();
    }

    private void load() {
        Arrays.stream(Items.values()).forEach(item -> {
            item.setItem(Practice.getInstance().getConfig().getItemStack("items." + item.toString() + ".item"));
            item.setSlot(Practice.getInstance().getConfig().getInt("items." + item.toString() + ".slot"));
            item.setEnabled(Practice.getInstance().getConfig().getBoolean("items." + item.toString() + ".enabled"));
        });
    }
}
