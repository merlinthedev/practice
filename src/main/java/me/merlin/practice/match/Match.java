package me.merlin.practice.match;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import me.merlin.practice.Practice;
import me.merlin.practice.kit.Kit;
import me.merlin.practice.match.events.MatchEndEvent;
import me.merlin.practice.match.events.MatchStartEvent;
import me.merlin.practice.profile.PlayerHandler;
import me.merlin.practice.profile.PlayerProfile;
import me.merlin.practice.profile.ProfileHandler;
import me.merlin.practice.util.Logger;
import me.merlin.practice.util.fanciful.FancyMessage;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
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
        Logger.success("Match added to handler!");

        teamOneLeader = Bukkit.getPlayer(teamOne.get(0));
        teamOneLeaderProfile = profileHandler.getProfile(teamOneLeader);

        teamOne.forEach(players::add);
        if (teamTwo != null) {
            teamTwoLeader = Bukkit.getPlayer(teamTwo.get(0));
            teamTwoLeaderProfile = profileHandler.getProfile(teamTwoLeader);
            teamTwo.forEach(players::add);
        }

        matchAction(player -> {
            Logger.success("Match action started!");

            PlayerProfile profile = profileHandler.getProfile(player);
            playerHandler.resetPlayer(player);
            if(kit != null) {
                player.getInventory().setContents(kit.getInventory());
                player.getInventory().setArmorContents(kit.getArmor());
            }

            Logger.success("Player reset!");


            player.updateInventory();
            player.setMaximumNoDamageTicks(kit != null ? kit.getDamageTicks() : 19);

            System.out.println(kit.getArmor());
            System.out.println(kit.getInventory());
            Logger.success("Inventory set!");
        });

        MatchStartEvent matchStartEvent = new MatchStartEvent(this);
        matchStartEvent.call();
        Logger.success("Match start event called!");

        new BukkitRunnable() {
            int i = 5;

            public void run() {
                if(matchState != MatchState.STARTING) {
                    this.cancel();
                    return;
                }

                if(i> 0) {
                    broadcast(ChatColor.YELLOW + "Match starting in " + ChatColor.RED + i + ChatColor.YELLOW + " seconds!");
                    playSound(Sound.NOTE_STICKS);
                }
                i--;
                if(i == -1) {
                    this.cancel();
                    broadcast(ChatColor.YELLOW + "Match started!");
                    playSound(Sound.NOTE_PLING);


                    matchState = MatchState.ACTIVE;
                    startTime = System.currentTimeMillis();
                    return;
                }

            }
        }.runTaskTimerAsynchronously(Practice.getInstance(), 20L, 20L);


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

    public void broadcast(String message) {
        matchAction(player -> player.sendMessage(message));

    }

    public void playSound(Sound sound) {
        matchAction(player -> player.playSound(player.getLocation(), sound, 20, 20));
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
