import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import lotr.common.entity.LOTREntities;

@Mod(modid="sauron", dependencies="required-after:lotr")

public class LOTREntitySauronMod {
	@Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e) {
        LOTREntities.registerCreature(LOTREntitySauronExtended.class, "Sauron", 1000, 0, 0);
    }
}