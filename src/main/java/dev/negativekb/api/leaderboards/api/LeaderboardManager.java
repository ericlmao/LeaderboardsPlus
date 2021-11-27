package dev.negativekb.api.leaderboards.api;

import dev.negativekb.api.leaderboards.core.structure.Leaderboard;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@SuppressWarnings("all")
public abstract class LeaderboardManager {

    @Getter
    @Setter
    private static LeaderboardManager instance;

    /**
     * Get a Leaderboard by its name
     *
     * @param name Name Input
     * @return If the leaderboard name is registered, return. If not, return empty.
     */
    public abstract Optional<Leaderboard> getLeaderboard(String name);

    /**
     * Registeres a Leaderboard to a plugin name
     *
     * @param plugin Plugin Name
     * @param type   Leaderboard
     */
    public abstract void register(String plugin, Leaderboard type);

    /**
     * UnRegisteres a Leaderboard by the name provided
     *
     * @param name Name Input
     */
    public abstract void unRegister(String name);

}
