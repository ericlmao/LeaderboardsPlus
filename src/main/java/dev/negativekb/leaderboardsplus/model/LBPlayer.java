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
            ArrayList<String> statistics = getStatistics();
            String stat = statistics.stream().filter(s -> s.split(":")[0].equalsIgnoreCase(identifier)).findFirst().orElse(null);
            if (stat == null)
                return;

            String[] info = stat.split(":");
            String name = info[0];
            int index = statistics.indexOf(stat);
            this.statistics.set(index, name + ":" + value);
        } else {
            this.statistics.add(identifier + ":" + value);
        }
    }


    public void deleteStatistic(String identifier) {
        String stat = getStatistics().stream().filter(s -> s.split(":")[0].equalsIgnoreCase(identifier)).findFirst().orElse(null);
        if (stat == null)
            return;

        this.statistics.remove(stat);
    }

    public double getStatistic(String identifier) {
        String stat = getStatistics().stream().filter(s -> s.split(":")[0].equalsIgnoreCase(identifier)).findFirst().orElse(null);
        if (stat == null)
            return 0;

        String[] info = stat.split(":");
        double value = 0;
        try {
            value = Double.parseDouble(info[1]);
        } catch (Exception ignored) {}

        return value;
    }

    public boolean isValidStatistic(String identifier) {
        String stat = getStatistics().stream().filter(s -> s.split(":")[0].equalsIgnoreCase(identifier)).findFirst().orElse(null);
        return stat != null;
    }
}
