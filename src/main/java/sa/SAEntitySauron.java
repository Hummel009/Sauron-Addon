package sa;

import lotr.common.entity.npc.LOTREntitySauron;
import net.minecraft.world.World;

public class SAEntitySauron
extends LOTREntitySauron {
    public SAEntitySauron(World world) {
        super(world);
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isRemote && this.getHealth() < this.getMaxHealth() && this.ticksExisted % 10 == 0) {
            this.setHealth(this.getHealth() - 2.0f);
            this.heal(0.5f);
        }
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(npcAttackDamage).setBaseValue(10.0);
    }
}