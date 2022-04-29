package me.merlin.practice.duel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.merlin.practice.kit.Kit;

import java.util.UUID;

@AllArgsConstructor
public class DuelRequest {

    @Getter private UUID sender;
    @Getter private Kit kit;
    @Getter private final long timestamp = System.currentTimeMillis();
}
