package me.merlin.practice.kit;

import lombok.Getter;
import me.merlin.practice.Practice;
import me.merlin.practice.kit.command.KitCommand;

import java.util.ArrayList;
import java.util.List;

public class KitHandler {

    @Getter private List<Kit> kitList = new ArrayList<>();


    public KitHandler() {
        Practice.getInstance().getCommand("kit").setExecutor(new KitCommand());
    }

    public void addKit(Kit kit) {
        kitList.add(kit);
    }

    public void removeKit(Kit kit) {
        kitList.remove(kit);
    }

    public Kit getKit(String name) {
        for (Kit kit : kitList) {
            if (kit.getName().equalsIgnoreCase(name)) {
                return kit;
            }
        }
        return null;
    }


    public List<Kit> getKits() {
        return kitList;
    }

}
