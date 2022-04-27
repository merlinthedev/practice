package me.merlin.practice.profile;

import lombok.Getter;
import lombok.Setter;
import me.merlin.practice.duel.DuelRequest;
import me.merlin.practice.match.Match;
import org.bukkit.Bukkit;
import org.bukkit.conversations.Conversation;

import java.util.ArrayList;
import java.util.List;


public class PlayerProfile {

    private long enderpearlCooldown;
    private PlayerState playerState;
    private Match match;
    private List<DuelRequest> duelRequests;
    private Conversation conversation;


    public PlayerProfile() {
        playerState = PlayerState.LOBBY;
        duelRequests = new ArrayList<>();

    }

    public enum PlayerState {
        LOBBY,
        QUEUE,
        MATCH
    }
}
