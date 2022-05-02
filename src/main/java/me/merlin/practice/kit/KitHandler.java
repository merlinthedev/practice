package me.merlin.practice.kit;

import lombok.Getter;
import me.merlin.practice.Practice;
import me.merlin.practice.kit.command.KitCommand;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitHandler {

    @Getter
    private List<Kit> kitList = new ArrayList<>();


    public KitHandler() {
        Practice.getInstance().getCommand("kit").setExecutor(new KitCommand());
    }

    private void load() {
        if (Practice.getInstance().getConfig().getConfigurationSection("kits") == null) return;

        Practice.getInstance().getConfig().getConfigurationSection("kits").getKeys(false).forEach(kitName -> {
            Kit kit = new Kit(kitName);

            kit.setDisplayName(Practice.getInstance().getConfig().getString("kits." + kitName + ".name"));

            if (Practice.getInstance().getConfig().getString("kits." + kitName + ".items") != null) {
                kit.setInventory(Practice.getInstance().getConfig().getStringList("kits." + kitName + ".items").toArray(new ItemStack[0]));

            }

            addKit(kit);
        });
    }

    public void addKit(Kit kit) {
        kitList.add(kit);
    }

    public void removeKit(Kit kit) {
        kitList.remove(kit);
    }

    public Kit getKit(String name) {
        for (Kit kit : kitList) {
            if (kit.getName().equalsIgnoreCase(name)) {
                return kit;
            }
        }
        return null;
    }


    public List<Kit> getKits() {
        return kitList;
    }

}
