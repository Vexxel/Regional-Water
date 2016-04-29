package com.velleth.regionalwater;

import net.minecraftforge.common.BiomeDictionary;

/**
 * Created by Zerren on 4/28/2016.
 */
public class LocationChecker {
    public static BiomeDictionary.Type[] validBiomes;
    public static BiomeDictionary.Type[] bannedBiomes;

    public static void initArrays() {
        validBiomes = new BiomeDictionary.Type[ConfigHandler.validBiomeDictionary.length];
        for (int i = 0; i < ConfigHandler.validBiomeDictionary.length; i++) {
            validBiomes[i] = BiomeDictionary.Type.getType(ConfigHandler.validBiomeDictionary[i]);
        }

        bannedBiomes = new BiomeDictionary.Type[ConfigHandler.bannedBiomeDictionary.length];
        for (int i = 0; i < ConfigHandler.bannedBiomeDictionary.length; i++) {
            bannedBiomes[i] = BiomeDictionary.Type.getType(ConfigHandler.bannedBiomeDictionary[i]);
        }

        for (BiomeDictionary.Type biome : validBiomes) {
            RWModContainer.log.info("Valid biomes: " + biome.name());
        }
    }

    public static boolean containsValidBiome(BiomeDictionary.Type[] currentTypes) {

        for (BiomeDictionary.Type currentType : currentTypes) {
            //iterates over valid biome types
            for (BiomeDictionary.Type validType : validBiomes) {
                //if the current type and valid type match
                if (currentType == validType) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsBannedBiome(BiomeDictionary.Type[] currentTypes) {

        for (BiomeDictionary.Type currentType : currentTypes) {
            //iterates over valid biome types
            for (BiomeDictionary.Type bannedType : bannedBiomes) {
                //if the current type and valid type match
                if (currentType.equals(bannedType)) {
                    //System.out.println("Banned biome");
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isValidDim(int dim) {
        for (int d : ConfigHandler.validDims) {
            if (d == dim) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBannedDim(int dim) {
        for (int d : ConfigHandler.bannedDims) {
            if (d == dim) {
                //System.out.println("Banned dimension");
                return true;
            }
        }
        return false;
    }
}
