package dev.negativekb.leaderboardsplus.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.UUID;

@Data
public class LBPlayer {

    private final UUID uuid;
    private ArrayList<String> statistics;

    public LBPlayer(UUID uuid) {
        this.uuid = uuid;
        this.statistics = new ArrayList<>();
    }

    public void setStatistic(String identifier, double value) {
        boolean valid = isValidStatistic(identifier);
        if (valid) {
            for (String statistic : getStatistics()) {
                String[] info = statistic.split(":");
                String name = info[0];
                if (name.equalsIgnoreCase(identifier)) {
                    int index = getStatistics().indexOf(statistic);
                    statistics.set(index, name + ":" + value);
                    return;
                }
            }
        } else {
            statistics.add(identifier + ":" + value);
        }
    }


    public void deleteStatistic(String identifier) {
        for (String statistic : getStatistics()) {
            String[] info = statistic.split(":");
            String name = info[0];
            if (name.equalsIgnoreCase(identifier)) {
                statistics.remove(statistic);
                return;
            }
        }
    }

    public double getStatistic(String identifier) {
        for (String statistic : getStatistics()) {
            String[] info = statistic.split(":");
            String name = info[0];
            if (name.equalsIgnoreCase(identifier)) {
                double value = 0;
                try {
                    String v = info[1];
                    value = Double.parseDouble(v);
                } catch (Exception ignored) {}

                return value;
            }

        }
        return 0;
    }

    public boolean isValidStatistic(String identifier) {
        for (String statistic : getStatistics()) {
            String[] info = statistic.split(":");
            String name = info[0];
            if (name.equalsIgnoreCase(identifier))
                return true;
        }

        return false;
    }
}
