package sa;

import lotr.common.entity.npc.LOTREntitySauron;
import net.minecraft.world.World;

public class SAEntitySauron extends LOTREntitySauron {
	public SAEntitySauron(World world) {
		super(world);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(npcAttackDamage).setBaseValue(10.0);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && getHealth() < getMaxHealth() && ticksExisted % 10 == 0) {
			setHealth(getHealth() - 2.0f);
			heal(0.5f);
		}
	}
}
