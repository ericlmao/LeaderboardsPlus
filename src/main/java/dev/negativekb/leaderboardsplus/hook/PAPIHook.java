package dev.negativekb.leaderboardsplus.hook;

import dev.negativekb.leaderboardsplus.LeaderboardsPlus;
import dev.negativekb.leaderboardsplus.managers.LBManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PAPIHook extends PlaceholderExpansion {
    private final LeaderboardsPlus plugin;

    public PAPIHook(LeaderboardsPlus plus) {
        plugin = plus;
    }

    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist() {
        return true;
    }

    /**
     * Because this is a internal class, this check is not needed
     * and we can simply return {@code true}
     *
     * @return Always true since it's an internal class.
     */
    @Override
    public boolean canRegister() {
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     * <br>For convienience do we return the author from the plugin.yml
     *
     * @return The name of the author as a String.
     */
    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>The identifier has to be lowercase and can't contain _ or %
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public @NotNull String getIdentifier() {
        return "leaderboardsplus";
    }

    /**
     * This is the version of the expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     * <p>
     * For convienience do we return the version from the plugin.yml
     *
     * @return The version as a String.
     */
    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    /**
     * This is the method called when a placeholder with our identifier
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param identifier A String containing the identifier/value.
     * @return possibly-null String of the requested identifier.
     */
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {

        if (player == null) {
            return "";
        }

        String[] paths = identifier.split("_");

        List<String> registeredStats = LBManager.getInstance().getRegisteredStatistics();
        String stat = registeredStats.stream().filter(s -> paths[0].equalsIgnoreCase(s)).findFirst().orElse(null);

        String noData = plugin.getConfig().getString("no-data-placeholder", "N/A");
        if (stat == null)
            return noData;


        DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.##");

        List<Map.Entry<UUID, Double>> entry = LBManager.getInstance().getSortedStatistics().getOrDefault(stat, null);
        if (entry == null)
            return noData;

        if (paths[1].equalsIgnoreCase("self")) {
            Map.Entry<UUID, Double> data = entry.stream().filter(uuidObjectEntry -> uuidObjectEntry.getKey().equals(player.getUniqueId())).findFirst().orElse(null);
            if (data == null)
                return noData;

            if (paths[2].equalsIgnoreCase("position"))
                return String.valueOf((entry.indexOf(data) + 1));

            if (paths[2].equalsIgnoreCase("value"))
                return df.format(data.getValue());

            return null;
        }

        if (paths[1].equalsIgnoreCase("position")) {
            if (paths[2].equalsIgnoreCase("name")) {
                int placement = 0;
                try {
                    placement = (Integer.parseInt(paths[3]) - 1);
                } catch (Exception ignored) {}

                Map.Entry<UUID, Double> data;
                try {
                    data = entry.get(placement);
                } catch (Exception e) {
                    return noData;
                }

                return Bukkit.getOfflinePlayer(data.getKey()).getName();
            }

            if (paths[2].equalsIgnoreCase("value")) {
                int placement = 0;
                try {
                    placement = (Integer.parseInt(paths[3]) - 1);
                } catch (Exception ignored) {}

                Map.Entry<UUID, Double> data;
                try {
                    data = entry.get(placement);
                }  catch (Exception e) {
                    return noData;
                }

                return df.format(data.getValue());
            }

            return null;
        }

        return null;
    }
}
