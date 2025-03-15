package me.lakoba.pureSkyblockCore.managers;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.World;
import java.util.Random;

public class EmptyWorldGenerator extends ChunkGenerator {
    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData chunkData = createChunkData(world);
        return chunkData;
    }
}
