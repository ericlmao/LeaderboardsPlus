package dev.negativekb.api.leaderboards.core.implementation;

import dev.negativekb.api.leaderboards.api.LeaderboardManager;
import dev.negativekb.api.leaderboards.api.LeaderboardPAPIHandler;
import dev.negativekb.api.leaderboards.core.structure.Leaderboard;
import dev.negativekb.api.leaderboards.core.structure.LeaderboardRequestType;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@SuppressWarnings("all")
public class LeaderboardsPlusPAPIHandlerProvider extends LeaderboardPAPIHandler {

    public LeaderboardsPlusPAPIHandlerProvider() {
        setInstance(this);
    }

    @Override
    public String handle(String statisticName, LeaderboardRequestType type, String input) {

        boolean findingPosition = false;
        int position = 0;
        try {
            position = Integer.parseInt(input);
            findingPosition = true;
        } catch (Exception ignored) {
        }

        LeaderboardManager manager = LeaderboardManager.getInstance();
        Optional<Leaderboard> lb = manager.getLeaderboard(statisticName);
        if (!lb.isPresent())
            return null;

        Leaderboard<?, ?> leaderboard = lb.get();
        Stream<Leaderboard.Entry<String, String>> parsedSorted = leaderboard.parsedSorted();

        AtomicReference<String> value = type.get(leaderboard, parsedSorted, findingPosition, input, position);
        return value.get();
    }
}
