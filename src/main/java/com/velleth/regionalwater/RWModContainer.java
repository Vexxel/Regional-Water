package com.velleth.regionalwater;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Created by Zerren on 4/27/2016.
 */

public class RWModContainer extends DummyModContainer {

    public RWModContainer() {
        super(new ModMetadata());
        ModMetadata meta = getMetadata();
        meta.modId = "regionalwater";
        meta.name = "Regional Water";
        meta.description = "Enables infinite water in only certain biomes and dimensions that are specified in the config file";
        meta.version = "1.7.10-1.0";
        meta.authorList = Arrays.asList("Velleth");
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }

    @Subscribe
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.init(event.getSuggestedConfigurationFile());
    }
}
