package com.github.hummel.sauron.init;

import com.github.hummel.sauron.entity.EntitySauron;
import lotr.common.entity.LOTREntities;

public class Entities {
	private Entities() {
	}

	public static void preInit() {
		LOTREntities.registerCreature(EntitySauron.class, "Sauron", 1000, 0, 0);
	}
}
