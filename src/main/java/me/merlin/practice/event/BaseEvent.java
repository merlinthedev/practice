package me.merlin.practice.event;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BaseEvent extends Event {

    @Getter
    private static HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public void call() {
        Bukkit.getPluginManager().callEvent(this);

    }
}
