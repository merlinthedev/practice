package me.merlin.practice.match;

import me.merlin.practice.Practice;
import me.merlin.practice.profile.PlayerProfile;
import me.merlin.practice.profile.ProfileHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MatchHandler {

    private List<Match> matchList = new ArrayList<>();

    public MatchHandler() {


        Bukkit.getPluginManager().registerEvents(new MatchListener(), Practice.getInstance());


    }

    public Match getMatch(Player player) {
        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        PlayerProfile practiceProfile = profileHandler.getProfile(player);

        if (practiceProfile.getPlayerState() == PlayerProfile.PlayerState.MATCH) {
            return practiceProfile.getMatch();
        }

//        if (practiceProfile.getPlayerState() == PlayerProfile.PlayerState.PARTY) {
//            Party party = practiceProfile.getParty();
//
//            if (party.getPartyState() == Party.PartyState.MATCH) {
//                return party.getMatch();
//            }
//        }
        return null;
    }

    public String getOpponentName(Player player) {
        if (getMatch(player) == null) return null;

        Match match = getMatch(player);
        boolean t1 = match.getTeamOne().contains(player.getUniqueId());

        return (t1 ? match.getTeamTwoLeader().getName() : match.getTeamOneLeader().getName())
//                + (match.isParty() ? "'s Team" : "")
                ;
    }

    public Match getMatch(Location location) {
        return null;
//        return matchList.stream().filter(m -> m.getEntry().spawn1().distance(location) < 500).findFirst().orElse(null);
    }

    public int getFighting() {
        return (int) Bukkit.getOnlinePlayers().stream().filter(p -> getMatch(p) != null).count();
    }

    public void addMatch(Match match) {
        matchList.add(match);
    }

    public void removeMatch(Match match) {
        matchList.remove(match);
    }
}
