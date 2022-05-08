package me.merlin.practice.match;

import me.merlin.practice.Practice;
import me.merlin.practice.profile.PlayerProfile;
import me.merlin.practice.profile.ProfileHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MatchHandler {

    private List<Match> matchList = new ArrayList<>();

    public MatchHandler() {
        Practice.getInstance().getServer().getPluginManager().registerEvents(new MatchListener(), Practice.getInstance());


    }

    public Match getMatch(Player player){
        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        PlayerProfile profile = profileHandler.getProfile(player);
        if(profile.getPlayerState() == PlayerProfile.PlayerState.MATCH) {
            return profile.getMatch();
        }
        return null;
    }

    public void addMatch(Match match){
        matchList.add(match);
    }

    public void removeMatch(Match match){
        matchList.remove(match);
    }
}
