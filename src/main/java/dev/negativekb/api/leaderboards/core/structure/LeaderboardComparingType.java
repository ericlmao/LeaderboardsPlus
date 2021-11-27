package dev.negativekb.api.leaderboards.core.structure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum LeaderboardComparingType {
    HIGHEST_TO_LOWEST("highest_to_lowest"),
    LOWEST_TO_HIGHEST("lowest_to_highest");

    private final String id;

    public static Optional<LeaderboardComparingType> getByString(String input) {
        return Arrays.stream(values())
                .filter(leaderboardComparingType -> leaderboardComparingType.getId().equalsIgnoreCase(input))
                .findFirst();
    }
}
