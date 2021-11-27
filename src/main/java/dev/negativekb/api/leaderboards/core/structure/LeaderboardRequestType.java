package dev.negativekb.api.leaderboards.core.structure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum LeaderboardRequestType {

    NAME("name"),
    VALUE("value"),
    POSITION("position");
    private final String id;

    public static Optional<LeaderboardRequestType> getByString(String input) {
        return Arrays.stream(values())
                .filter(leaderboardRequestType -> leaderboardRequestType.getId().equalsIgnoreCase(input))
                .findFirst();
    }

    public AtomicReference<String> get(Leaderboard<?, ?> leaderboard, Stream<Leaderboard.Entry<String, String>> parsedSorted, boolean findingPosition, String input, int position) {
        DecimalFormat df = new DecimalFormat("###,###,###,###.##");
        AtomicReference<String> value = new AtomicReference<>();
        String error = (leaderboard.errorMessage() == null ? "&cError!" : leaderboard.errorMessage());
        switch (this) {
            case NAME: {
                if (findingPosition)
                    value.set(parsedSorted.skip(position - 1).map(Leaderboard.Entry::getKey).findFirst().orElse(error));
                else
                    value.set(error);
                break;
            }

            case VALUE: {
                if (findingPosition)
                    value.set(parsedSorted.skip(position - 1).map(Leaderboard.Entry::getValue).findFirst().orElse(error));
                else
                    value.set(parsedSorted.filter((entry -> entry.getKey().equals(input))).map(Leaderboard.Entry::getValue)
                            .findFirst().orElse(error));
                break;
            }

            case POSITION: {
                if (!findingPosition) {
                    AtomicInteger pos = new AtomicInteger(1);
                    Optional<Leaderboard.Entry<String, String>> entry = parsedSorted.filter(e -> {
                        if (e.getKey().equals(input))
                            return true;
                        pos.incrementAndGet();
                        return false;
                    }).findFirst();

                    value.set(entry.isPresent() ? df.format(pos.get()) : error);
                }
            }
        }

        return value;
    }
}
