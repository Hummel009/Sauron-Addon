package com.github.hummel.sauron;

import com.github.hummel.sauron.init.Entities;
import com.github.hummel.sauron.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@SuppressWarnings({"PublicField", "WeakerAccess"})
@Mod(modid = "sauron", dependencies = "required-after:lotr", useMetadata = true)
public class Main {
	@SidedProxy(clientSide = "com.github.hummel.sauron.proxy.ClientProxy", serverSide = "com.github.hummel.sauron.proxy.ServerProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Entities.preInit();

		proxy.preInit();
	}
}