package com.github.hummel.sauron.entity.ai;

import com.github.hummel.sauron.entity.EntitySauron;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemSauronMace;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;

public class EntityAISauronUseMace extends EntityAIBase {
	private final EntitySauron theSauron;
	private final World theWorld;
	private int attackTick;

	public EntityAISauronUseMace(EntitySauron sauron) {
		theSauron = sauron;
		theWorld = theSauron.worldObj;
		setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		int targets = 0;
		List<EntityLivingBase> nearbyEntities = theWorld.getEntitiesWithinAABB(EntityLivingBase.class, theSauron.boundingBox.expand(6.0, 6.0, 6.0));

		for (EntityLivingBase entity : nearbyEntities) {
			if (entity.isEntityAlive()) {
				if (entity instanceof EntityPlayer) {
					EntityPlayer entityplayer = (EntityPlayer) entity;
					if (!entityplayer.capabilities.isCreativeMode && (LOTRLevelData.getData(entityplayer).getAlignment(theSauron.getFaction()) < 0.0F || theSauron.getAttackTarget() == entityplayer)) {
						++targets;
					}
				} else if (theSauron.getFaction().isBadRelation(LOTRMod.getNPCFaction(entity))) {
					++targets;
				} else if (theSauron.getAttackTarget() == entity || entity instanceof EntityLiving && ((EntityLiving) entity).getAttackTarget() == theSauron) {
					++targets;
				}
			}
		}

		return targets >= 4 || targets > 0 && theSauron.getRNG().nextInt(100) == 0;
	}

	@Override
	public boolean continueExecuting() {
		return theSauron.getIsUsingMace();
	}

	@Override
	public void startExecuting() {
		attackTick = 40;
		theSauron.setIsUsingMace(true);
	}

	@Override
	public void resetTask() {
		attackTick = 40;
		theSauron.setIsUsingMace(false);
	}

	@Override
	public void updateTask() {
		attackTick = Math.max(attackTick - 1, 0);
		if (attackTick == 0) {
			attackTick = 40;
			LOTRItemSauronMace.useSauronMace(theSauron.getEquipmentInSlot(0), theWorld, theSauron);
			theSauron.setIsUsingMace(false);
		}

	}
}
