package me.lakoba.pureSkyblockCore;

import me.lakoba.pureSkyblockCore.command.IslandCommand;
import me.lakoba.pureSkyblockCore.listeners.ProtectionListener;
import me.lakoba.pureSkyblockCore.managers.DatabaseManager;
import me.lakoba.pureSkyblockCore.managers.IslandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PureSkyblockCore extends JavaPlugin {

    private static PureSkyblockCore instance;
    private DatabaseManager databaseManager;
    private IslandManager islandManager;


    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        databaseManager = new DatabaseManager();
        islandManager = new IslandManager(this);

        getServer().getPluginManager().registerEvents(new ProtectionListener(), this);
        getCommand("is").setExecutor(new IslandCommand(islandManager));

        getLogger().info("SkyblockCore byl úspěšně spuštěn!");
    }

    @Override
    public void onDisable() {
        databaseManager.close();
        getLogger().info("SkyblockCore byl vypnut.");
    }

    public static PureSkyblockCore getInstance() {
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public IslandManager getIslandManager() {
        return islandManager;
    }
}
