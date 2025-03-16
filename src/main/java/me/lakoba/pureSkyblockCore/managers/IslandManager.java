package me.lakoba.pureSkyblockCore.managers;

import me.lakoba.pureSkyblockCore.PureSkyblockCore;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;
import java.io.IOException;
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
        World world = generateFlatOceanWorld(player.getName());
        Location center = new Location(world, 0, 67, 0);

        schematicLoader.loadSchematic("main_island");
        schematicLoader.pasteSchematic(center);

        generateRandomIslands(world);

        player.teleport(center);
        player.sendMessage("§aVáš ostrov byl vytvořen!");
        saveIslandData(player.getName());
    }

    private World generateFlatOceanWorld(String worldName) {
        WorldCreator wc = new WorldCreator(worldName);
        wc.generateStructures(false);
        wc.generator(new ChunkGenerator() {
            @Override
            public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
                ChunkGenerator.ChunkData chunkData = createChunkData(world);
                for (int i = 0; i < 64; i++) {
                    chunkData.setRegion(0, i, 0, 16, i + 1, 16, Material.WATER);
                }
                return chunkData;
            }
        });
        World world = Bukkit.createWorld(wc);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, true);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, true);

        return world;
    }

    private void generateRandomIslands(World world) {
        int maxIslands = config.getInt("max_islands", 60);
        String[] schematics = new File(plugin.getDataFolder(), "schematics").list();

        if (schematics == null || schematics.length == 0) return;

        for (int i = 0; i < maxIslands; i++) {
            int x = random.nextInt(10000) - 5000;
            int z = random.nextInt(10000) - 5000;
            String schematic = schematics[random.nextInt(schematics.length)].replace(".schem", "");

            schematicLoader.loadSchematic(schematic);
            schematicLoader.pasteSchematic(new Location(world, x, 67, z));
        }
    }

    private void saveIslandData(String ownerName) {
        File dataFile = new File(plugin.getDataFolder(), "islands.yml");
        YamlConfiguration dataConfig = YamlConfiguration.loadConfiguration(dataFile);

        dataConfig.set(ownerName + ".level", 1);

        File playerDataFile = new File(plugin.getDataFolder(), "players.yml");
        YamlConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);

        playerDataConfig.set(ownerName + ".island", ownerName);
        playerDataConfig.set(ownerName + ".rank", "owner");

        try {
            dataConfig.save(dataFile);
            playerDataConfig.save(playerDataFile);
            plugin.reloadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Location getIslandLocation(Player player) {
        String dataWorldName = plugin.getPlayerDataFile().getString(player.getName() + ".island");
        return new Location(Bukkit.getWorld(dataWorldName), 0, 67, 0);
    }
}