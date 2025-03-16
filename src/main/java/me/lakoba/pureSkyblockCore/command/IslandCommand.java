package me.lakoba.pureSkyblockCore.command;

import me.lakoba.pureSkyblockCore.PureSkyblockCore;
import me.lakoba.pureSkyblockCore.managers.ExpansionManager;
import me.lakoba.pureSkyblockCore.managers.InvitationManager;
import me.lakoba.pureSkyblockCore.managers.IslandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class IslandCommand implements CommandExecutor {

    private final IslandManager islandManager;
    private final InvitationManager invitationManager;
    private final ExpansionManager expansionManager;

    public IslandCommand(IslandManager islandManager, InvitationManager invitationManager, ExpansionManager expansionManager) {
        this.islandManager = islandManager;
        this.invitationManager = invitationManager;
        this.expansionManager = expansionManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();

        if (label.equalsIgnoreCase("is")) {
            if (args.length == 0) {
                player.sendMessage("Použití: /is create | /is invite <hráč> | /is expand");
                return true;
            }

            switch (args[0].toLowerCase()) {
                case "create":
                    islandManager.createIsland(player);
                    return true;

                case "invite":
                    Player target = player.getServer().getPlayer(args[1]);
                    if (target != null) {
                        invitationManager.sendInvite(playerUUID, target.getUniqueId());
                        player.sendMessage("Pozvánka odeslána " + target.getName());
                    }
                    return true;

                case "expand":
                    expansionManager.expandIsland(playerUUID, player);
                    return true;

                case "teleport":
                    player.teleport(islandManager.getIslandLocation(player));
                    return true;
            }
        }
        return false;
    }

}
