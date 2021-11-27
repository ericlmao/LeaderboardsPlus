package dev.negativekb.api.leaderboards.core.implementation;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import dev.negativekb.api.leaderboards.api.LeaderboardManager;
import dev.negativekb.api.leaderboards.core.structure.Leaderboard;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

@SuppressWarnings("all")
public class LeaderboardsPlusAPIProvider extends LeaderboardManager {

    @Getter
    private final Multimap<String, Leaderboard> registeredLeaderboards = ArrayListMultimap.create();

    public LeaderboardsPlusAPIProvider() {
        setInstance(this);
    }

    @Override
    public Optional<Leaderboard> getLeaderboard(String name) {
        return getRegisteredLeaderboards().entries().stream()
                .filter(e -> e.getValue().getName().equalsIgnoreCase(name)).map(Map.Entry::getValue)
                .findFirst();
    }

    @Override
    public void register(String plugin, Leaderboard type) {
        if (getRegisteredLeaderboards().get(plugin).stream()
                .anyMatch(leaderboard -> leaderboard.getName().equalsIgnoreCase(type.getName())))
            return;

        registeredLeaderboards.put(plugin, type);
        System.out.println("[LeaderboardsPlus] Registered leaderboard `" + type.getName() + "`");
    }

    @Override
    public void unRegister(String name) {
        registeredLeaderboards.entries()
                .removeIf(e -> e.getValue().getName().equalsIgnoreCase(name));
    }
}
