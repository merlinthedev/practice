package me.merlin.practice.kit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class Kit {

    @Getter private final String name;

    @Getter @Setter private String displayName;

    @Getter @Setter private ItemStack[] inventory;
    @Getter @Setter private ItemStack[] armor;


    @Setter @Getter private List<UUID> unrankedQueue = new ArrayList<>();
    @Setter @Getter private List<UUID> unrankedMatch = new ArrayList<>();
    @Setter @Getter private List<UUID> rankedQueue = new ArrayList<>();
    @Setter @Getter private List<UUID> rankedMatch = new ArrayList<>();







}
