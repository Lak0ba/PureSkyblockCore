package me.lakoba.pureSkyblockCore.command;

import me.lakoba.pureSkyblockCore.PureSkyblockCore;
import me.lakoba.pureSkyblockCore.managers.IslandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IslandCommand implements CommandExecutor {

    private final IslandManager islandManager;

    public IslandCommand(IslandManager islandManager) {
        this.islandManager = islandManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Použij /is create pro vytvoření ostrova.");
            return true;
        }

        if (args[0].equalsIgnoreCase("create")) {
            islandManager.createIsland(player);
            return true;
        }

        return false;
    }

}
