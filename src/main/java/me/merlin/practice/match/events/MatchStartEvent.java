package me.merlin.practice.match.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.merlin.practice.event.BaseEvent;
import me.merlin.practice.match.Match;

@Getter
@AllArgsConstructor
public class MatchStartEvent extends BaseEvent {

    private Match match;

}
