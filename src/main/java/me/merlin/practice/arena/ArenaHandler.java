package me.merlin.practice.arena;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ArenaHandler {

    @Getter @Setter
    private List<Arena> arenas;

    public ArenaHandler() {
        arenas = new ArrayList<>();

    }

    // Add an arena
    public void addArena(Arena arena) {
        arenas.add(arena);
    }

    // Remove an arena
    public void removeArena(Arena arena) {
        arenas.remove(arena);
    }




}
