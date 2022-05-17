package me.merlin.practice.kit.command;

import me.merlin.practice.Practice;
import me.merlin.practice.kit.Kit;
import me.merlin.practice.kit.KitHandler;
import me.merlin.practice.util.Logger;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitCommand implements CommandExecutor {

    private final Practice plugin = Practice.getInstance();


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            KitHandler kitHandler = plugin.getKitHandler();
            switch (strings.length) {
                case 0:
                    player.sendMessage("§8-=- §fKit §8-=-");
                    player.sendMessage("§c/kit create <name> §8- §fCreate a kit");
                    player.sendMessage("§c/kit delete <name> §8- §fDelete a kit");
                    player.sendMessage("§c/kit list §8- §fList all kits");
                    player.sendMessage("§c/kit setDisplayItem <kit name> §8- §fSet a kit's display item");
                    player.sendMessage("§c/kit setInventory <kit name> §8- §fSet a kit's display name");
                    player.sendMessage("§c/kit see <kit name> §8- §fSee a kit's informaion");
                    player.sendMessage("§c/kit give <kit name> §8- §fGive a kit to a player");

                    break;
                case 1:
                    if (strings[0].equalsIgnoreCase("list")) {
                        player.sendMessage("§8-=- §fKit List §8-=-");
                        for (String kit : plugin.getConfig().getConfigurationSection("kits").getKeys(false)) {
                            player.sendMessage("§c" + kit);
                            Logger.success("Kit " + kit + " listed");
                        }

                    }
                    break;
                case 2:
                    if (strings[0].equalsIgnoreCase("create")) {
                        Kit kit = new Kit(strings[1]);

                        //todo: it brokey? i think it is an issue with the commands in the (config/plugin).yml file but im not sure
                        plugin.getConfig().getConfigurationSection("kits").set(strings[1], kit.getName());
                        plugin.saveConfig();
                        Logger.success(strings[1] + " has been created!");


                        kit.setDisplayName(strings[1]);
                        kit.setInventory(player.getInventory().getContents());
                        kit.setArmor(player.getInventory().getArmorContents());
                        kitHandler.addKit(kit);

                        return true;

                    }
                    if (strings[0].equalsIgnoreCase("delete")) {
                        plugin.getConfig().getConfigurationSection("kits").set(strings[1], null);
                        Kit kit = kitHandler.getKit(strings[1]);
                        kitHandler.removeKit(kit);
                        player.sendMessage("§cKit §f" + strings[1] + " §cdeleted!");
                        plugin.saveConfig();
                        return true;
                    }

                    if (strings[0].equalsIgnoreCase("setdisplayitem")) {
                        Kit kit = kitHandler.getKit(strings[1]);
                        if (player.getInventory().getItemInHand() != null) {
                            plugin.getConfig().getConfigurationSection("kits").set(strings[1] + ".item", player.getInventory().getItemInHand());
                            plugin.saveConfig();
                        }
                        kit.setDisplayItem(player.getInventory().getItemInHand());
                        return true;
                    }

                    if (strings[0].equalsIgnoreCase("setinventory")) {
                        if (strings[1] == null) {
                            player.sendMessage("§cYou must specify a kit!");
                            return true;
                        }
                        String name = strings[1];

                        if (kitHandler.getKit(name) != null) {
                            Kit kit = kitHandler.getKit(name);
                            if (player.getGameMode() == GameMode.CREATIVE) {
                                player.sendMessage("§cYou must be in survival mode to set the inventory!");
                                return true;
                            }
                            kit.setInventory(player.getInventory().getContents().clone());
                            kit.setArmor(player.getInventory().getArmorContents().clone());

                            Practice.getInstance().getConfig().set("kits." + name + ".inventory", kit.getInventory());
                            Practice.getInstance().getConfig().set("kits." + name + ".armor", kit.getArmor());
                            Practice.getInstance().saveConfig();

                            player.sendMessage("§aInventory set for kit §e" + kit.getName() + "§a!");
                            return true;
                        } else{
                            player.sendMessage("§cKit not found!");
                            return true;
                        }
                    }

                    if (strings[0].equalsIgnoreCase("see")) {
                        if (strings[1] == null) {
                            player.sendMessage("§cYou must specify a kit!");
                            return true;
                        }

                        Kit kit = kitHandler.getKit(strings[1]);
                        if (kit == null) {
                            player.sendMessage("§cKit not found!");
                            return true;
                        }

                        player.sendMessage("§aKit: §e" + kit.getName());
                        player.sendMessage("§aDisplay Item: §e" + kit.getDisplayItem());
                        player.sendMessage("§aInventory: " + kit.getInventory().length);
                        player.sendMessage("§aArmor: " + kit.getArmor().length);

                        player.sendMessage("§aKit Contents:");
                        for (ItemStack item : kit.getInventory()) {
                            if (item != null) {
                                player.sendMessage("§e" + item.getType().name());
                            }
                        }

                        for(ItemStack item : kit.getArmor()){
                            if(item != null){
                                player.sendMessage("§e" + item.getType().name());
                            }
                        }
                        return true;
                    }

                    if (strings[0].equalsIgnoreCase("give")) {
                        if (strings[1] == null) {
                            player.sendMessage("§cYou must specify a kit!");
                        }

                        Kit kit = kitHandler.getKit(strings[1]);
                        if (kit == null) {
                            player.sendMessage("§cKit not found!");
                        }


                        player.getInventory().setContents(kit.getInventory());
                        player.getInventory().setArmorContents(kit.getArmor());
                        player.updateInventory();
                    }

                    break;
            }

        } else {
            Logger.error("You must be a player to use this command!");
        }

        return false;
    }
}
