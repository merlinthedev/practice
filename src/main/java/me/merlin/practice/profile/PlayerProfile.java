package me.merlin.practice.profile;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import me.merlin.practice.duel.DuelRequest;
import me.merlin.practice.kit.Kit;
import me.merlin.practice.match.Match;
import org.bukkit.Bukkit;
import org.bukkit.conversations.Conversation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PlayerProfile {

    private long enderpearlCooldown;
    @Getter @Setter private PlayerState playerState;
    @Getter @Setter private Match match;
    @Getter private List<DuelRequest> duelRequests;

    @Getter @Setter private int hits;
    @Getter @Setter private int combo;
    @Getter @Setter private int longestCombo;
    @Getter @Setter private int thrownPots;
    @Getter @Setter private int fullyLandedPots;


    private Conversation conversation;
    private Map<Kit, Integer> eloMap;



    public PlayerProfile() {
        playerState = PlayerState.LOBBY;
        duelRequests = new ArrayList<>();
        eloMap = Maps.newHashMap();
    }

    // get player elo
    public int getElo(Kit kit) {
        return eloMap.getOrDefault(kit, 0);
    }

    public void setElo(Kit kit, int elo) {
        eloMap.put(kit, elo);
    }


    public enum PlayerState {
        LOBBY,
        QUEUE,
        MATCH
    }
}
