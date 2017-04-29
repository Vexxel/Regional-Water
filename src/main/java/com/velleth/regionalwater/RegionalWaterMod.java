package com.velleth.regionalwater;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.CreateFluidSourceEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = RegionalWaterMod.MODID, name = "Regional Water")
public class RegionalWaterMod {

    public static final String MODID = "regionalwater";

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(RegionalWaterMod.class);
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigHandler.reloadConfigValues();
        }
    }

    @SubscribeEvent
    public static void onCreateFluidSource(CreateFluidSourceEvent event) {
        IBlockState state = event.getState();
        if (state.getBlock() == Blocks.WATER || state.getBlock() == Blocks.FLOWING_WATER) {
            if (!TransformMethods.shouldGenerateSource(event.getWorld(), event.getPos())) {
                event.setResult(Result.DENY);
            }
        }
    }
}
