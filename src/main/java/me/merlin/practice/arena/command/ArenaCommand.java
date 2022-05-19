package me.merlin.practice.arena.command;

import me.merlin.practice.Practice;
import me.merlin.practice.arena.Arena;
import me.merlin.practice.arena.ArenaHandler;
import me.merlin.practice.config.ConfigHandler;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("practice.arena")) {
                ArenaHandler arenaHandler = Practice.getInstance().getArenaHandler();
                ConfigHandler configHandler = Practice.getInstance().getConfigHandler();
                switch (strings.length) {
                    case 0:
                        usage(player);
                        break;
                    case 1:
                        if (strings[0].equalsIgnoreCase("list")) {
                            player.sendMessage("§8-=- §fArena List §8-=-");
                            // Get all arenas from hashmap
                            arenaHandler.getArenas().forEach((key, value) -> player.sendMessage("§7" + key.getName() + " §8- §f" + value));

                        } else {
                            usage(player);
                        }
                        break;
                    case 2:
                        if (strings[0].equalsIgnoreCase("create")) {
                            if (strings[1] != null) {
                                Arena arena = new Arena(strings[1]);
                                // check if the id of the arena is already taken
                                if (arenaHandler.getArena(arena.getId()) == null) {
                                    arenaHandler.addArena(arena);
                                    // add the arena to the config
                                    configHandler.getArenaFile().set("Arenas." + arena.getName() + ".id", arena.getId());
                                    configHandler.safeArenaFile();
                                    player.sendMessage("§aArena created.");

                                } else {
                                    player.sendMessage("§cArena already exists.");
                                }
                            }

                        } else if (strings[0].equalsIgnoreCase("delete")) {
                            if (strings[1] != null) {
                                Arena arena = arenaHandler.getArena(strings[1]);
                                if (arena != null) {
                                    arenaHandler.removeArena(arena);
                                    configHandler.getArenaFile().set("Arenas." + arena.getName(), null);
                                    configHandler.safeArenaFile();
                                    player.sendMessage("§cArena §f" + arena.getName() + "§c was deleted!");
                                } else {
                                    player.sendMessage("§cArena with this id does not exist!");
                                }
                            }
                        } else if (strings[0].equalsIgnoreCase("tp")) {
                            if (strings[1] != null) {
                                Arena arena = arenaHandler.getArena(strings[1]);
                                if (arena != null) {
                                    teleport(player, arena.getCenter().getX(), arena.getCenter().getY(), arena.getCenter().getZ());
                                    player.sendMessage("§aTeleported to arena §f" + arena.getName());
                                } else {
                                    player.sendMessage("§cArena with this id does not exist!");
                                }
                            } else {
                                player.sendMessage("§cPlease specify an arena!");
                            }
                        } else {
                            usage(player);
                        }
                        break;
                    case 3:
                        if (strings[0].equalsIgnoreCase("set")) {
                            if (strings[1] != null) {
                                Arena arena = arenaHandler.getArena(strings[1]);
                                if (arena != null) {
                                    if (strings[2] != null) {
                                        if (strings[2].equalsIgnoreCase("1")) {
                                            arena.setSpawnOne(player.getLocation());
                                            // Safe player x, y, z to config
                                            configHandler.getArenaFile().set("Arenas." + arena.getName() + ".spawnOne.x", player.getLocation().getX());
                                            configHandler.getArenaFile().set("Arenas." + arena.getName() + ".spawnOne.y", player.getLocation().getY());
                                            configHandler.getArenaFile().set("Arenas." + arena.getName() + ".spawnOne.z", player.getLocation().getZ());
                                            configHandler.safeArenaFile();
                                            player.sendMessage("§aSpawn 1 set!");
                                        }
                                        if (strings[2].equalsIgnoreCase("2")) {
                                            arena.setSpawnTwo(player.getLocation());
                                            configHandler.getArenaFile().set("Arenas." + arena.getName() + ".spawnTwo.x", player.getLocation().getX());
                                            configHandler.getArenaFile().set("Arenas." + arena.getName() + ".spawnTwo.y", player.getLocation().getY());
                                            configHandler.getArenaFile().set("Arenas." + arena.getName() + ".spawnTwo.z", player.getLocation().getZ());
                                            configHandler.safeArenaFile();
                                            player.sendMessage("§aSpawn 2 set!");
                                        }
                                        if (strings[2].equalsIgnoreCase("center")) {
                                            arena.setCenter(player.getLocation());

                                            configHandler.getArenaFile().set("Arenas." + arena.getName() + ".center.x", player.getLocation().getX());
                                            configHandler.getArenaFile().set("Arenas." + arena.getName() + ".center.y", player.getLocation().getY());
                                            configHandler.getArenaFile().set("Arenas." + arena.getName() + ".center.z", player.getLocation().getZ());
                                            configHandler.safeArenaFile();
                                            player.sendMessage("§aCenter set!");
                                        }
                                    } else {
                                        usage(player);
                                    }
                                } else {
                                    player.sendMessage("§cArena with this name doesn't exist!");
                                }
                            } else {
                                usage(player);
                            }
                        } else {
                            usage(player);
                        }

                }
            }
        }


        return false;
    }

    private void usage(Player player) {
        player.sendMessage("§8-=- §fArena §8-=-");
        player.sendMessage("§7/arena list §8- §fList all arenas");
        player.sendMessage("§7/arena create <name> §8- §fCreate an arena");
        player.sendMessage("§7/arena delete <name> §8- §fDelete an arena");
        player.sendMessage("§7/arena set <name> <location> §8- §fSet an arena's spawn points");
    }

    private void teleport(Player player, double x, double y, double z) {
        player.teleport(new Location(player.getWorld(), x, y, z));
        player.sendMessage("§aTeleported!");

    }
}
