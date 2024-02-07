package com.github.hummel.sauron.proxy;

import com.github.hummel.sauron.entity.EntitySauron;
import com.github.hummel.sauron.render.RenderSauron;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntitySauron.class, new RenderSauron());
	}
}