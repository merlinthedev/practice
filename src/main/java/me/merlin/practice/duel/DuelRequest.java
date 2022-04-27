package me.merlin.practice.duel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public class DuelRequest {

    @Getter private UUID sender;
    @Getter private final long timestamp = System.currentTimeMillis();
}
