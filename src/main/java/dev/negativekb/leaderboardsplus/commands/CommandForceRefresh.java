package dev.negativekb.leaderboardsplus.commands;

import dev.negativekb.leaderboardsplus.managers.LBManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandForceRefresh implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        LBManager.getInstance().refresh();
        sender.sendMessage("done");
        return true;
    }
}
