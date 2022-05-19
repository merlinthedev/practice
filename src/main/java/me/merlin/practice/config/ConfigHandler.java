package me.merlin.practice.config;

import com.google.common.collect.Maps;
import lombok.Getter;
import me.merlin.practice.Practice;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class ConfigHandler {


    @Getter private YamlConfiguration kitsFile;
    @Getter private YamlConfiguration arenaFile;

    private Map<UUID, FileConfiguration> fileConfigMap;


    public ConfigHandler() {
        loadArenaFile();
        loadKitsFile();

        fileConfigMap = Maps.newHashMap();


    }

    private void loadKitsFile() {
        File KFile = new File(Practice.getInstance().getDataFolder(), "kits.yml");
        if (!KFile.exists()) {
            try {
                KFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        kitsFile = YamlConfiguration.loadConfiguration(KFile);

    }

    private void saveKitsFile() {
        try {
            kitsFile.save(new File(Practice.getInstance().getDataFolder(), "kits.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadArenaFile() {
        File AFile = new File(Practice.getInstance().getDataFolder(), "arena.yml");
        if (!AFile.exists()) {
            try {
                AFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        arenaFile = YamlConfiguration.loadConfiguration(AFile);

    }

    private void safeArenaFile() {
        try {
            arenaFile.save(new File(Practice.getInstance().getDataFolder(), "arena.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getFile(UUID uuid) {
        return fileConfigMap.get(uuid);
    }



}
