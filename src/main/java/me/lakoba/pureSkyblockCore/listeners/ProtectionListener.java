package me.lakoba.pureSkyblockCore.listeners;

import me.lakoba.pureSkyblockCore.PureSkyblockCore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ProtectionListener implements Listener {

    private PureSkyblockCore plugin;

    public ProtectionListener(PureSkyblockCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        String dataWorldName = plugin.getPlayerDataFile().getString(player.getUniqueId() + ".island");
        Integer isLevel = plugin.getDataFile().getInt(dataWorldName + ".level");

        Location loc = player.getLocation();

        if (world.getName().equalsIgnoreCase(player.getName()) || world.getName().equalsIgnoreCase(dataWorldName)) {
            if (isLevel == 1) {
                if (loc.getX() >= 1000 || loc.getZ() >= 1000 || loc.getY() <= -1000 || loc.getZ() <= -1000) {
                    teleportBack(player, 1000);
                }
            } else if (isLevel == 2) {
                if (loc.getX() >= 2000 || loc.getZ() >= 2000 || loc.getY() <= -2000 || loc.getZ() <= -2000) {
                    teleportBack(player, 2000);
                }
            } else if (isLevel == 3) {
                if (loc.getX() >= 3000 || loc.getZ() >= 3000 || loc.getY() <= -3000 || loc.getZ() <= -3000) {
                    teleportBack(player, 3000);
                }
            } else if (isLevel == 4) {
                if (loc.getX() >= 4000 || loc.getZ() >= 4000 || loc.getY() <= -4000 || loc.getZ() <= -4000) {
                    teleportBack(player, 4000);
                }
            } else if (isLevel == 5) {
                if (loc.getX() >= 5000 || loc.getZ() >= 5000 || loc.getY() <= -5000 || loc.getZ() <= -5000) {
                    teleportBack(player, 5000);
                }
            } else if (loc.getY() <= -30 && loc.getBlock().getType() == Material.WATER) {
                player.setHealth(player.getHealth() - 1);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        String dataWorldName = plugin.getPlayerDataFile().getString(player.getName() + ".island");
        Integer isLevel = plugin.getDataFile().getInt(dataWorldName + ".level");

        Location loc = player.getLocation();

        if (world.getName().equalsIgnoreCase(player.getName()) || world.getName().equalsIgnoreCase(dataWorldName)) {
            if (isLevel == 1) {
                if (loc.getX() > 1000 || loc.getZ() > 1000 || loc.getY() < -1000 || loc.getZ() < -1000) {
                    teleportBack(player, 1000);
                }
            } else if (isLevel == 2) {
                if (loc.getX() >= 2000 || loc.getZ() > 2000 || loc.getY() < -2000 || loc.getZ() < -2000) {
                    teleportBack(player, 2000);
                }
            } else if (isLevel == 3) {
                if (loc.getX() >= 3000 || loc.getZ() < 3000 || loc.getY() < -3000 || loc.getZ() < -3000) {
                    teleportBack(player, 3000);
                }
            } else if (isLevel == 4) {
                if (loc.getX() >= 4000 || loc.getZ() > 4000 || loc.getY() < -4000 || loc.getZ() < -4000) {
                    teleportBack(player, 4000);
                }
            } else if (isLevel == 5) {
                if (loc.getX() >= 5000 || loc.getZ() > 5000 || loc.getY() < -5000 || loc.getZ() < -5000) {
                    teleportBack(player, 5000);
                }
            } else if (loc.getY() <= -30 && loc.getBlock().getType() == Material.WATER) {
                player.setHealth(player.getHealth() - 1);
            }
        }
    }

    private void teleportBack(Player player, int maxDistance) {
        Location loc = player.getLocation();
        double x = Math.min(Math.max(loc.getX(), -maxDistance), maxDistance);
        double y = Math.max(loc.getY(), -maxDistance);
        double z = Math.min(Math.max(loc.getZ(), -maxDistance), maxDistance);
        player.teleport(new Location(player.getWorld(), x, y, z));
        player.sendMessage("§cNemůžeš se dostat mimo svůj ostrov!");
    }

}
