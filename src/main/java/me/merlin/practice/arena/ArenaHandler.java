package me.merlin.practice.arena;

import lombok.Getter;
import lombok.Setter;
import me.merlin.practice.Practice;
import me.merlin.practice.arena.command.ArenaCommand;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class ArenaHandler {

    @Getter
    @Setter
    private Map<Arena, Integer> arenas;

    private Practice plugin;

    public ArenaHandler() {
        plugin = Practice.getInstance();
        plugin.getCommand("arena").setExecutor(new ArenaCommand());
        // Register ArenaListener


        arenas = new HashMap<>();
        load();
    }

    private void load() {
        plugin.getConfigHandler().getArenaFile().getConfigurationSection("Arenas").getKeys(false).forEach(key -> {
            Arena arena = new Arena(key);
            Location center = new Location(plugin.getServer().getWorlds().get(0), plugin.getConfigHandler().getArenaFile().getDouble("Arenas." + key + ".center.x"), plugin.getConfigHandler().getArenaFile().getDouble("Arenas." + key + ".center.y"), plugin.getConfigHandler().getArenaFile().getDouble("Arenas." + key + ".center.z"));
            arena.setCenter(center);
            Location spawnOne = new Location(plugin.getServer().getWorlds().get(0), plugin.getConfigHandler().getArenaFile().getDouble("Arenas." + key + ".spawnOne.x"), plugin.getConfigHandler().getArenaFile().getDouble("Arenas." + key + ".spawnOne.y"), plugin.getConfigHandler().getArenaFile().getDouble("Arenas." + key + ".spawnOne.z"));
            arena.setSpawnOne(spawnOne);
            Location spawnTwo = new Location(plugin.getServer().getWorlds().get(0), plugin.getConfigHandler().getArenaFile().getDouble("Arenas." + key + ".spawnTwo.x"), plugin.getConfigHandler().getArenaFile().getDouble("Arenas." + key + ".spawnTwo.y"), plugin.getConfigHandler().getArenaFile().getDouble("Arenas." + key + ".spawnTwo.z"));
            arena.setSpawnTwo(spawnTwo);

            addArena(arena);
        });
    }

    // Add an arena
    public void addArena(Arena arena) {
        arenas.put(arena, arenas.size() + 1);
    }

    // Remove an arena
    public void removeArena(Arena arena) {
        arenas.remove(arena);
    }

    // Get an arena
    public Arena getArena(int id) {
        for (Arena arena : arenas.keySet()) {
            if (arenas.get(arena) == id) {
                return arena;
            }
        }
        return null;
    }

    // Get an arena
    public Arena getArena(String name) {
        for (Arena arena : arenas.keySet()) {
            if (arena.getName().equalsIgnoreCase(name)) {
                return arena;
            }
        }
        return null;
    }


}
