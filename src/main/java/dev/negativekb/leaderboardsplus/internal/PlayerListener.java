package dev.negativekb.leaderboardsplus.internal;

import dev.negativekb.leaderboardsplus.LeaderboardsPlus;
import dev.negativekb.leaderboardsplus.managers.LBManager;
import dev.negativekb.leaderboardsplus.model.LBPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerListener implements Listener {

    private final LBManager manager;
    private final LeaderboardsPlus plugin;
    public PlayerListener(LeaderboardsPlus plugin) {
        manager = LBManager.getInstance();
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        // Adds a Death
        Player player = event.getEntity();
        FileConfiguration config = plugin.getConfig();
        boolean countDeaths = config.getBoolean("internal-statistics.deaths", true);
        boolean countKills = config.getBoolean("internal-statistics.deaths", true);

        if (countDeaths) {
            LBPlayer diedPlayer = manager.getProfile(player.getUniqueId());
            String diedStat = "Deaths";
            diedPlayer.setStatistic(diedStat, (diedPlayer.getStatistic(diedStat) + 1));
        }

        if (player.getKiller() != null) {
            // Adds Kills
            Player killer = player.getKiller();
            if (countKills) {
                LBPlayer killerPlayer = manager.getProfile(killer.getUniqueId());
                String killsStat = "Kills";
                killerPlayer.setStatistic(killsStat, (killerPlayer.getStatistic(killsStat) + 1));
            }
        }

    }
}
