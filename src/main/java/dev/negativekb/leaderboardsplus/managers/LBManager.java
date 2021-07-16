package dev.negativekb.leaderboardsplus.managers;

import com.google.gson.Gson;
import dev.negativekb.leaderboardsplus.LeaderboardsPlus;
import dev.negativekb.leaderboardsplus.model.LBPlayer;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class LBManager {

    private ArrayList<LBPlayer> players = new ArrayList<>();
    private final HashMap<String, List<Map.Entry<UUID, Double>>> sortedStatistics = new HashMap<>();
    private final ArrayList<String> registeredStatistics = new ArrayList<>();
    private static LBManager instance;
    private final String dataFile;

    public LBManager(LeaderboardsPlus plugin) {
        instance = this;

        this.dataFile = plugin.getDataFolder().getAbsolutePath() + "/stats.json";

        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int interval = plugin.getConfig().getInt("data-save-interval", 120);
        new Timer().runTaskTimer(plugin, 0, interval);
        new Refresh().runTaskTimer(plugin, 0, interval);
    }

    public void save() throws IOException {
        Gson gson = new Gson();
        File file = new File(dataFile);
        file.getParentFile().mkdir();
        file.createNewFile();

        Writer writer = new FileWriter(file, false);
        gson.toJson(players, writer);
        writer.flush();
        writer.close();
    }

    public void load() throws IOException {
        Gson gson = new Gson();
        File file = new File(dataFile);
        if (file.exists()) {
            Reader reader = new FileReader(file);
            LBPlayer[] t = gson.fromJson(reader, LBPlayer[].class);
            players = new ArrayList<>(Arrays.asList(t));
        }
    }

    public ArrayList<LBPlayer> getPlayers() {
        return players;
    }

    public LBPlayer getProfile(UUID uuid) {
        LBPlayer lbPlayer = getPlayers().stream().filter(lbPlayer1 -> lbPlayer1.getUuid().equals(uuid)).findFirst().orElse(null);
        if (lbPlayer == null) {
            lbPlayer = new LBPlayer(uuid);
            players.add(lbPlayer);
        }

        return lbPlayer;
    }

    public void registerStatistics(String... statisticIdentifiers) {
        for (String statisticIdentifier : statisticIdentifiers) {
            this.registeredStatistics.add(statisticIdentifier);
            System.out.println("Added statistic: " + statisticIdentifier);
        }
    }

    public HashMap<String, List<Map.Entry<UUID, Double>>> getSortedStatistics() {
        return sortedStatistics;
    }

    public ArrayList<String> getRegisteredStatistics() {
        return registeredStatistics;
    }

    public static LBManager getInstance() {
        return instance;
    }

    private class Timer extends BukkitRunnable {

        @Override
        public void run() {
            try {
                save();
                load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void refresh() {
        sortedStatistics.clear();
        ArrayList<LBPlayer> players = getPlayers();
        getRegisteredStatistics().forEach(id -> {
            HashMap<UUID, Double> stats = new HashMap<>();

            players.stream().filter(lbPlayer -> !lbPlayer.isValidStatistic(id)).forEach(lbPlayer -> stats.put(lbPlayer.getUuid(), lbPlayer.getStatistic(id)));

            List<Map.Entry<UUID, Double>> sorted = stats.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
            Collections.reverse(sorted);

            sortedStatistics.put(id, sorted);
        });
    }
    private class Refresh extends BukkitRunnable {

        @Override
        public void run() {
            refresh();
        }
    }
}
