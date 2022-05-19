package me.merlin.practice.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.Random;


public class Arena {

    @Getter @Setter private int id;
    @Getter private boolean active;
    @Getter private String name;
    @Getter @Setter private Location spawnOne;
    @Getter @Setter private Location spawnTwo;
    @Getter @Setter private Location center;


    public Arena(String string) {
        id = new Random().nextInt(999999);
        this.name = string;
        this.active = false;
    }
}
