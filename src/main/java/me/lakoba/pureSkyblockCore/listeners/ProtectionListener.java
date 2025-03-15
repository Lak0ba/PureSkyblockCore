package me.lakoba.pureSkyblockCore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ProtectionListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        event.setCancelled(true);
        event.getPlayer().sendMessage("§cNemůžeš stavět mimo svůj ostrov!");
    }

}
