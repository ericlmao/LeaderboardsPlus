package dev.negativekb.api.leaderboards.hook;

import dev.negativekb.api.leaderboards.LeaderboardsPlus;
import dev.negativekb.api.leaderboards.api.LeaderboardPAPIHandler;
import dev.negativekb.api.leaderboards.core.structure.LeaderboardRequestType;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.Optional;

public class PAPILeaderboardExpansion extends PlaceholderExpansion {
    private final LeaderboardsPlus plugin;
    private final LeaderboardPAPIHandler papiHandler;

    public PAPILeaderboardExpansion(LeaderboardsPlus plus) {
        plugin = plus;
        papiHandler = LeaderboardPAPIHandler.getInstance();
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
     * Because this is an internal class, this check is not needed, and we can simply return {@code true}
     *
     * @return Always true since it's an internal class.
     */
    @Override
    public boolean canRegister() {
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     * <br>For convenience do we return the author from the plugin.yml
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
        return "leaderboard";
    }

    /**
     * This is the version of the expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     * <p>
     * For convenience do we return the version from the plugin.yml
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
        String statisticName = paths[0];
        // Name, Key, Position
        String leaderboardStatistic = paths[1];
        // Number, Name, UUID, etc.
        String input = paths[2];
        if (input.equals("self"))
            input = player.getName();

        // Leaderboard Statistic Finder
        Optional<LeaderboardRequestType> byString = LeaderboardRequestType.getByString(leaderboardStatistic);
        if (!byString.isPresent())
            return "Invalid Leaderboard Request Type";

        LeaderboardRequestType type = byString.get();

        return papiHandler.handle(statisticName, type, input);
    }
}
