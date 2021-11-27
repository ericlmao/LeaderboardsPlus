package dev.negativekb.api.leaderboards.api;

import dev.negativekb.api.leaderboards.core.structure.LeaderboardRequestType;
import lombok.Getter;
import lombok.Setter;

public abstract class LeaderboardPAPIHandler {

    @Getter
    @Setter
    private static LeaderboardPAPIHandler instance;

    public abstract String handle(String statisticName, LeaderboardRequestType type, String input);
}
