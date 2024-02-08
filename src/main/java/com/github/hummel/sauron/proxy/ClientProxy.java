package com.github.hummel.sauron.proxy;

import com.github.hummel.sauron.entity.EntitySauron;
import com.github.hummel.sauron.render.RenderSauron;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy implements CommonProxy {
	@Override
	public void preInit() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySauron.class, new RenderSauron());
	}
}