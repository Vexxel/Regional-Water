package com.velleth.regionalwater;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * Created by Zerren on 4/27/2016.
 */
@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions({"com.velleth.regionalwater"})
public class RegionalWaterCore implements IFMLLoadingPlugin
{
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{ RWClassTransformer.class.getName() };
    }

    @Override
    public String getModContainerClass() {
        return RWModContainer.class.getName();
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) { }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}