package com.velleth.regionalwater;

import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

/**
 * Created by Zerren on 4/27/2016.
 */
public class TransformMethods {
    public static boolean waterInitialized = false;

    public static boolean shouldGenerateSource(World world, int x, int y, int z) {
        if (!waterInitialized) {
            waterInitialized = true;
            LocationChecker.initArrays();
        }
        int currentDim = world.provider.dimensionId;
        BiomeDictionary.Type[] currentBiomeTypes = BiomeDictionary.getTypesForBiome(world.getBiomeGenForCoords(x, z));

        //Y-axis check
        if (y < ConfigHandler.waterLower || y > ConfigHandler.waterUpper) {
            System.out.println("Denied. Reason: Too high/low");
            return false;
        }

        if (LocationChecker.isBannedDim(currentDim) || LocationChecker.containsBannedBiome(currentBiomeTypes)) {
            return false;
        }

        return ConfigHandler.reverse || LocationChecker.isValidDim(currentDim) && LocationChecker.containsValidBiome(currentBiomeTypes);
    }
}