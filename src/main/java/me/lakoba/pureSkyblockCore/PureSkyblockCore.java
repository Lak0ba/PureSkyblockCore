package me.lakoba.pureSkyblockCore;

import me.lakoba.pureSkyblockCore.command.IslandCommand;
import me.lakoba.pureSkyblockCore.managers.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class PureSkyblockCore extends JavaPlugin {

    private IslandManager islandManager;
    private IslandCommand commandManager;
    private InvitationManager invitationManager;
    private ExpansionManager expansionManager;

    File playerDataFile = new File(this.getDataFolder(), "players.yml");
    YamlConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);

    File dataFile = new File(this.getDataFolder(), "islands.yml");
    YamlConfiguration dataConfig = YamlConfiguration.loadConfiguration(dataFile);

    @Override
    public void onEnable() {
        saveDefaultConfig(); // Načtení konfigurace

        this.islandManager = new IslandManager(this);
        this.invitationManager = new InvitationManager();
        this.expansionManager = new ExpansionManager(this);
        this.commandManager = new IslandCommand( islandManager, invitationManager, expansionManager);

        getServer().getPluginManager().registerEvents(new me.lakoba.pureSkyblockCore.listeners.ProtectionListener(this), this);

        getCommand("is").setExecutor(commandManager);

        getLogger().info("Skyblock plugin byl úspěšně zapnut!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Skyblock plugin byl úspěšně vypnut!");
    }

    public void reloadData() {
        playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public YamlConfiguration getDataFile() {
        return dataConfig;
    }

    public YamlConfiguration getPlayerDataFile() {
        return playerDataConfig;
    }
}
