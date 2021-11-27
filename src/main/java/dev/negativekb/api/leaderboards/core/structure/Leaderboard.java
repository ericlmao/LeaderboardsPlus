package dev.negativekb.api.leaderboards.core.structure;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
@SuppressWarnings("all")
public abstract class Leaderboard<K, V extends Comparable<V>> {

    private static final Comparator<Entry> highestToLowest = Comparator.<Entry, Comparable>comparing(o1 -> o1.value).reversed();
    private static final Comparator<Entry> lowestToHighest = Comparator.<Entry, Comparable>comparing(o1 -> o1.value);
    private final List<K> keys = new ArrayList<>();
    private final String name;
    private final LeaderboardComparingType comparingType;
    private List<Entry<K, V>> sorted = Collections.synchronizedList(new ArrayList<>());

    public abstract V getValue(K key);

    public abstract String parseKey(K key);

    public abstract String parseValue(V value);

    public abstract String errorMessage();

    public void update() {
        update(keys, false);
    }

    public void update(Collection<K> keys, boolean replace) {
        List<Entry<K, V>> entries = new ArrayList<>(sorted);
        keys.forEach(k -> {
            entries.removeIf(e -> e.getKey().equals(k));
            entries.add(new Entry<>(k, getValue(k), comparingType));
        });
        if (replace)
            this.keys.addAll(keys);

        Collections.sort(entries);
        sorted = Collections.synchronizedList(entries);
    }

    public Stream<Entry<String, String>> parsedSorted() {
        return sorted.stream().map(e -> new Entry<>(parseKey(e.getKey()), parseValue(e.getValue()), comparingType));
    }

    @Data
    public static class Entry<K, V extends Comparable<V>> implements Comparable<Entry<K, V>> {
        private final K key;
        private final V value;
        private final LeaderboardComparingType comparingType;

        public Entry(K key, V value, LeaderboardComparingType comparingType) {
            this.key = key;
            this.value = value;
            this.comparingType = comparingType;
        }

        @Override
        public int compareTo(@NotNull Leaderboard.Entry<K, V> o) {
            if (comparingType.equals(LeaderboardComparingType.HIGHEST_TO_LOWEST))
                return highestToLowest.compare(this, o);
            else if (comparingType.equals(LeaderboardComparingType.LOWEST_TO_HIGHEST))
                return lowestToHighest.compare(this, o);
            else
                throw new NullPointerException("LeaderboardComparingType cannot be null!");
        }
    }
}
