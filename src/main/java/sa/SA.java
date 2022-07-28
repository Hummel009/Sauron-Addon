package sa;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import lotr.common.entity.LOTREntities;

@Mod(modid="sa", version="1.0", dependencies="required-after:lotr")
public class SA {
	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent e) {
		LOTREntities.registerCreature(SAEntitySauron.class, "Sauron", 1000, 0, 0);
	}
}
