package me.merlin.practice.items;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum Items {

    UNRANKED(null, 0, false),
    RANKED(null, 0, false),
    LEAVE_QUEUE(null, 0, false);


    @Setter @Getter private ItemStack item;
    @Setter @Getter private int slot;
    @Setter @Getter private boolean enabled;

}
