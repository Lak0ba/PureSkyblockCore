package me.lakoba.pureSkyblockCore.managers;

import me.lakoba.pureSkyblockCore.PureSkyblockCore;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Random;

public class IslandManager {
    private final PureSkyblockCore plugin;
    private final FileConfiguration config;
    private final Random random = new Random();
    private final SchematicLoader schematicLoader;

    public IslandManager(PureSkyblockCore plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.schematicLoader = new SchematicLoader();
    }

    public void createIsland(Player player) {
        World world = generateWaterWorld("skyblock_world");
        Location center = new Location(world, 0, 100, 0);

        schematicLoader.loadSchematic("main_island");
        schematicLoader.pasteSchematic(center);

        generateRandomIslands(world);

        player.teleport(center);
        player.sendMessage("§aVáš ostrov byl vytvořen!");
    }

    private World generateWaterWorld(String worldName) {
        WorldCreator wc = new WorldCreator(worldName);
        wc.generator(new EmptyWorldGenerator());
        World world = Bukkit.createWorld(wc);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, true);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, true);

        return world;
    }

    private void generateRandomIslands(World world) {
        int maxIslands = config.getInt("max_islands", 60);
        int distance = config.getInt("island_min_distance", 50);
        String[] schematics = new File(plugin.getDataFolder(), "schematics").list();

        if (schematics == null || schematics.length == 0) return;

        for (int i = 0; i < maxIslands; i++) {
            int x = random.nextInt(5000) - 2500;
            int z = random.nextInt(5000) - 2500;
            String schematic = schematics[random.nextInt(schematics.length)].replace(".schem", "");

            schematicLoader.loadSchematic(schematic);
            schematicLoader.pasteSchematic(new Location(world, x, 100, z));
        }
    }
}
