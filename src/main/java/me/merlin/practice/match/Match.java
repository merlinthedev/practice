package me.merlin.practice.match;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.merlin.practice.Practice;
import me.merlin.practice.kit.Kit;
import me.merlin.practice.profile.PlayerProfile;
import me.merlin.practice.profile.ProfileHandler;
import me.merlin.practice.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class Match {

    @Getter private final Kit kit;
    private final List<UUID> teamOne;
    private final List<UUID> teamTwo;


    private Player teamOneLeader;
    private Player teamTwoLeader;

    private PlayerProfile teamOneLeaderProfile;
    private PlayerProfile teamTwoLeaderProfile;

    private List<UUID> players = new ArrayList<>();

    private MatchState matchState = MatchState.STARTING;


    public void start() {
        MatchHandler matchHandler = Practice.getInstance().getMatchHandler();
        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();

        matchHandler.addMatch(this);

        Logger.success("Starting match with kit " + kit.getName());






    }

    public enum MatchState{
        STARTING, ACTIVE, ENDING
    }
}
