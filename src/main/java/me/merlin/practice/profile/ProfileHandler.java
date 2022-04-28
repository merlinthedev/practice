package me.merlin.practice.profile;

import com.google.common.collect.Maps;
import me.merlin.practice.Practice;
import me.merlin.practice.profile.command.ProfileCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class ProfileHandler {

    private Map<UUID, PlayerProfile> profileMap;

    public ProfileHandler() {
        profileMap = Maps.newHashMap();


        Practice.getInstance().getCommand("profile").setExecutor(new ProfileCommand());
        Bukkit.getServer().getPluginManager().registerEvents(new ProfileListener(), Practice.getInstance());
    }

    public void addPlayer(Player player) {
        profileMap.put(player.getUniqueId(), new PlayerProfile());
    }

    public void removeProfile(Player player) {
        profileMap.remove(player.getUniqueId());
    }

    public boolean hasProfile(Player player) {
        return profileMap.containsKey(player.getUniqueId());
    }

    public PlayerProfile getProfile(Player player) {
        return profileMap.get(player.getUniqueId());
    }
}
