package me.merlin.practice.match;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.merlin.practice.Practice;
import me.merlin.practice.kit.Kit;
import me.merlin.practice.match.events.MatchEndEvent;
import me.merlin.practice.profile.PlayerHandler;
import me.merlin.practice.profile.PlayerProfile;
import me.merlin.practice.profile.ProfileHandler;
import me.merlin.practice.util.Logger;
import me.merlin.practice.util.fanciful.FancyMessage;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class Match {

    @Getter
    private final Kit kit;
    @Getter
    private final List<UUID> teamOne;
    @Getter
    private final List<UUID> teamTwo;


    private Player teamOneLeader;
    private Player teamTwoLeader;

    private PlayerProfile teamOneLeaderProfile;
    private PlayerProfile teamTwoLeaderProfile;

    @Getter
    private List<UUID> players = new ArrayList<>();
    @Getter
    private List<UUID> spectators = new ArrayList<>();
    @Getter
    private List<UUID> alive = new ArrayList<>();
    @Getter
    private List<UUID> dead = new ArrayList<>();
    @Getter
    private List<UUID> exempt = new ArrayList<>();
    @Getter
    private List<Item> items = new ArrayList<>();
    @Getter
    private List<Arrow> arrows = new ArrayList<>();

    private long startTime = 0;
    private long endTime = 0;

    private MatchState state = MatchState.STARTING;


    @Getter
    private MatchState matchState = MatchState.STARTING;


    public void start() {

        // TODO MATCH STARTING LOGIC
        MatchHandler matchHandler = Practice.getInstance().getMatchHandler();
        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        PlayerHandler playerHandler = Practice.getInstance().getPlayerHandler();

        matchHandler.addMatch(this);

        teamOneLeader = Bukkit.getPlayer(teamOne.get(0));
        teamOneLeaderProfile = profileHandler.getProfile(teamOneLeader);

        teamOne.forEach(players::add);
        if (teamTwo != null) {
            teamTwoLeader = Bukkit.getPlayer(teamTwo.get(0));
            teamTwoLeaderProfile = profileHandler.getProfile(teamTwoLeader);
            teamTwo.forEach(players::add);
        }

        matchAction(player -> {
            playerHandler.resetPlayer(player);

            PlayerProfile profile = profileHandler.getProfile(player);

            player.getInventory().setContents(kit.getInventory());
            player.getInventory().setArmorContents(kit.getArmor());
            player.updateInventory();
        });




        Logger.success("Starting match with kit " + kit.getName());

    }

    //TODO MATCH ENDING LOGIC

    @SneakyThrows
    public void end(boolean t1) {

        if (matchState == MatchState.ENDING) return;


        MatchHandler matchHandler = Practice.getInstance().getMatchHandler();

        matchState = MatchState.ENDING;
        endTime = System.currentTimeMillis();

        ProfileHandler profileHandler = Practice.getInstance().getProfileHandler();
        PlayerHandler playerHandler = Practice.getInstance().getPlayerHandler();


        MatchEndEvent matchEndEvent = new MatchEndEvent(this);
        matchEndEvent.call();

        Bukkit.getScheduler().scheduleSyncDelayedTask(Practice.getInstance(), () -> {

            items.forEach(Item::remove);
            arrows.forEach(Arrow::remove);

            matchAction(player -> {
                if (kit != null) teamOne.stream().forEach(u1 -> kit.getUnrankedMatch().remove(u1));
                if (kit != null) teamOne.stream().forEach(u1 -> kit.getRankedMatch().remove(u1));

                if (teamTwo != null && kit != null) teamTwo.stream().forEach(u1 -> kit.getUnrankedMatch().remove(u1));
                if (teamTwo != null && kit != null) teamTwo.stream().forEach(u1 -> kit.getRankedMatch().remove(u1));

                if (player != null) {
                    PlayerProfile playerProfile = profileHandler.getProfile(player);

                    playerProfile.setHits(0);
                    playerProfile.setCombo(0);
                    playerProfile.setLongestCombo(0);
                    playerProfile.setThrownPots(0);
                    playerProfile.setFullyLandedPots(0);


                    playerHandler.giveItems(player);
                    playerHandler.teleportSpawn(player);
                    player.setGameMode(GameMode.SURVIVAL);


                    return;
                }
            });

            matchHandler.removeMatch(this);
        }, 60L);

    }


    //TODO change player state on death

    public void addDeath(Player player, MatchDeathReason matchDeathReason, Player killer) {
        if (dead.contains(player.getUniqueId()) || matchState == MatchState.ENDING) return;
        dead.add(player.getUniqueId());


        player.setGameMode(GameMode.CREATIVE);


        matchAction(players -> {
            if (players != null)
                if (!exempt.contains(players.getUniqueId())) {
                    players.hidePlayer(player);
                }


        });


        PlayerHandler playerHandler = Practice.getInstance().getPlayerHandler();
        Bukkit.getScheduler().scheduleSyncDelayedTask(Practice.getInstance(), () -> {
            playerHandler.resetPlayer(player);
        }, 5L);

        switch (matchDeathReason) {
            case DISCONNECTED: {
                matchAction(p -> {
//                    p.sendMessage(Messages.PLAYER_DEATH_DISCONNECTED.getMessage().replace("%player%", getEntryTeam(player).getPrefix() + player.getName()));
                    p.sendMessage(player.getName() + " disconnected");
                });
                exempt.add(player.getUniqueId());
                break;
            }
            case KILLED: {
                matchAction(p -> {
//                    p.sendMessage(Messages.PLAYER_DEATH_KILLED.getMessage().replace("%player%", getEntryTeam(player).getPrefix() + player.getName()).replace("%killer%", p.getScoreboard().getEntryTeam(killer.getName()).getPrefix() + killer.getName()));
                    p.sendMessage(player.getName() + " was killed by " + killer.getName());
                });
                break;
            }
            case DIED: {
                matchAction(p -> {
//                    p.sendMessage(Messages.PLAYER_DEATH_DIED.getMessage().replace("%player%", getEntryTeam(player).getPrefix() + player.getName()));
                    p.sendMessage(player.getName() + " died");
                });
                break;
            }
            case LEFT: {
                matchAction(p -> {
//                    p.sendMessage(Messages.PLAYER_DEATH_LEFT.getMessage().replace("%player%", getEntryTeam(player).getPrefix() + player.getName()));
                    p.sendMessage(player.getName() + " left");
                });
                exempt.add(player.getUniqueId());
                break;
            }
        }
        if (teamTwo == null) {
            if (getAlive(true) == 1) {
                end(true);
            }
        } else {
            if (getAlive(true) == 0) end(false);
            if (getAlive(false) == 0) end(true);
        }
    }

    public void matchAction(Consumer<? super Player> action) {
        players.forEach(u -> {
            Player player = Bukkit.getPlayer(u);

            if (player != null)

                action.accept(player);
        });
    }

    public void matchAction(Consumer<? super Player> action, boolean t1) {
        (t1 ? teamOne : teamTwo).forEach(u -> {
            Player player = Bukkit.getPlayer(u);

            if (player != null)

                action.accept(player);
        });
    }

    public int getAlive(boolean t1) {
        int alive = 0;
        for (UUID uuid : t1 ? teamOne : teamTwo) {
            if (!dead.contains(uuid)) alive += 1;
        }
        return alive;
    }


    public enum MatchState {
        STARTING, ACTIVE, ENDING
    }

    public enum MatchDeathReason {
        KILLED, DIED, DISCONNECTED, LEFT
    }

    private Team getEntryTeam(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        return scoreboard.getTeams().stream().filter(t -> t.getEntries().contains(player.getName())).findFirst().orElse(null);

    }
}
