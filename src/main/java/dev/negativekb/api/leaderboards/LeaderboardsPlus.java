package dev.negativekb.api.leaderboards;

import dev.negativekb.api.leaderboards.core.implementation.LeaderboardsPlusAPIProvider;
import dev.negativekb.api.leaderboards.core.implementation.LeaderboardsPlusPAPIHandlerProvider;
import dev.negativekb.api.leaderboards.hook.PAPILeaderboardExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LeaderboardsPlus extends JavaPlugin {

    @Override
    public void onEnable() {

        new LeaderboardsPlusAPIProvider();
        new LeaderboardsPlusPAPIHandlerProvider();

        // Small check to make sure that PlaceholderAPI is installed
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PAPILeaderboardExpansion(this).register();
            Bukkit.getLogger().info("[LeaderboardsPlus] Successfully hooked into PlaceholderAPI");
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
