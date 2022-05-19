package me.merlin.practice.kit;

import lombok.Getter;
import me.merlin.practice.Practice;
import me.merlin.practice.kit.command.KitCommand;
import me.merlin.practice.util.Logger;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitHandler {

    @Getter
    private List<Kit> kitList;

    private final Practice plugin;


    public KitHandler() {
        plugin = Practice.getInstance();

        kitList = new ArrayList<>();
        Practice.getInstance().getCommand("kit").setExecutor(new KitCommand());

        load();
    }

    private void load() {
//        if (plugin.getConfig().getConfigurationSection("kits") == null) return;
//
//        plugin.getConfig().getConfigurationSection("kits").getKeys(false).forEach(kitName -> {
//            // Log every kit to console
//            //Logger.info("Loading kit: " + kitName);
//            Kit kit = new Kit(kitName);
//
//            kit.setDisplayName(plugin.getConfig().getString("kits." + kitName + ".display"));
//            //Logger.info("Display name: " + kit.getDisplayName());
////            kit.setDisplayItem((ItemStack) plugin.getConfig().getInt("kits." + kitName + ".item"));
//            ItemStack dp = new ItemStack(plugin.getConfig().getInt("kits." + kitName + ".item"));
//            kit.setDisplayItem(dp);
//           // Logger.info("Display item: " + kit.getDisplayItem());
//
//            if (Practice.getInstance().getConfig().getString("kits." + kitName + ".inventory") != null) {
//                kit.setInventory(((List<ItemStack>)plugin.getConfig().get("kits." + kitName + ".inventory")).toArray(new ItemStack[0]));
//                kit.setArmor(((List<ItemStack>)plugin.getConfig().get("kits." + kitName + ".armor")).toArray(new ItemStack[0]));
//                //Logger.success("Loaded kit " + kit.getName() + " , Length:" + kit.getInventory().length + " , " + kit.getArmor().length);
//
//
//            }
//
//
//
//
//            addKit(kit);
//        });

        if(plugin.getConfigHandler().getKitsFile().getConfigurationSection("kits") == null) return;

        plugin.getConfigHandler().getKitsFile().getConfigurationSection("kits").getKeys(false).forEach(kitName -> {
            Kit kit = new Kit(kitName);
            kit.setDisplayName(plugin.getConfigHandler().getKitsFile().getString("kits." + kitName + ".display"));
            ItemStack dp = new ItemStack(plugin.getConfigHandler().getKitsFile().getInt("kits." + kitName + ".item"));
            kit.setDisplayItem(dp);



            if(plugin.getConfigHandler().getKitsFile().getString("kits." + kitName + ".inventory") != null) {
                kit.setInventory(((List<ItemStack>)plugin.getConfigHandler().getKitsFile().get("kits." + kitName + ".inventory")).toArray(new ItemStack[0]));
                kit.setArmor(((List<ItemStack>)plugin.getConfigHandler().getKitsFile().get("kits." + kitName + ".armor")).toArray(new ItemStack[0]));
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
        return kitList.stream().filter(k -> k.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }


    public List<Kit> getKits() {
        return kitList;
    }

}
