package com.github.hummel.sauron;

import com.github.hummel.sauron.entity.EntitySauron;
import com.github.hummel.sauron.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import lotr.common.entity.LOTREntities;

@Mod(modid = "sauron", dependencies = "required-after:lotr", useMetadata = true)
public class Main {
	@SidedProxy(clientSide = "com.github.hummel.sauron.proxy.ClientProxy", serverSide = "com.github.hummel.sauron.proxy.ServerProxy")
	private static CommonProxy proxy;

	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent e) {
		LOTREntities.registerCreature(EntitySauron.class, "Sauron", 1000, 0, 0);
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}
}