package com.velleth.regionalwater;

import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

/**
 * Created by Zerren on 4/27/2016.
 */
public class TransformMethods {
    private static boolean waterInitialized = false;

    public static boolean shouldGenerateSource(World world, int x, int y, int z) {
        //If the arrays haven't been initialized yet. Only runs once per game session and is called on first instance of water updating
        if (!waterInitialized) {
            waterInitialized = true;
            LocationChecker.initArrays();
        }
        int currentDim = world.provider.dimensionId;

        //Ocean dimension
        if (LocationChecker.isOceanDim(currentDim)) {
            return true;
        }
        //Banned dimension
        if (LocationChecker.isBannedDim(currentDim)) {
            return false;
        }
        //Y-axis check
        if (y < ConfigHandler.waterLower || y > ConfigHandler.waterUpper) {
            return false;
        }

        //Banned biome
        BiomeDictionary.Type[] currentBiomeTypes = BiomeDictionary.getTypesForBiome(world.getBiomeGenForCoords(x, z));
        if (LocationChecker.containsBannedBiome(currentBiomeTypes)) {
            return false;
        }

        //Config set to reverse, or valid dim and biome
        return ConfigHandler.reverse || LocationChecker.isValidDim(currentDim) && LocationChecker.containsValidBiome(currentBiomeTypes);
    }
}