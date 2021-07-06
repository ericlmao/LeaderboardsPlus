package dev.negativekb.leaderboardsplus;

import dev.negativekb.leaderboardsplus.commands.CommandForceRefresh;
import dev.negativekb.leaderboardsplus.hook.PAPIHook;
import dev.negativekb.leaderboardsplus.internal.PlayerListener;
import dev.negativekb.leaderboardsplus.managers.LBManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LeaderboardsPlus extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();

        new LBManager(this);


        LBManager.getInstance().registerStatistics(
                "Kills", "Deaths"
        );

        // Small check to make sure that PlaceholderAPI is installed
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PAPIHook(this).register();
            Bukkit.getLogger().info("[LeaderboardsPlus] Successfully hooked into PlaceholderAPI");
        }


        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        getCommand("forcerefresh").setExecutor(new CommandForceRefresh());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
