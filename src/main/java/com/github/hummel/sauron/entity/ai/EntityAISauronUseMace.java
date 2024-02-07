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
	private final EntitySauron sauron;
	private final World world;
	private int attackTick;

	public EntityAISauronUseMace(EntitySauron sauron) {
		this.sauron = sauron;
		world = this.sauron.worldObj;
		setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		int targets = 0;
		List<EntityLivingBase> nearbyEntities = world.getEntitiesWithinAABB(EntityLivingBase.class, sauron.boundingBox.expand(6.0, 6.0, 6.0));

		for (EntityLivingBase entity : nearbyEntities) {
			if (entity.isEntityAlive()) {
				if (entity instanceof EntityPlayer) {
					EntityPlayer entityplayer = (EntityPlayer) entity;
					if (!entityplayer.capabilities.isCreativeMode && (LOTRLevelData.getData(entityplayer).getAlignment(sauron.getFaction()) < 0.0F || sauron.getAttackTarget() == entityplayer)) {
						++targets;
					}
				} else if (sauron.getFaction().isBadRelation(LOTRMod.getNPCFaction(entity))) {
					++targets;
				} else if (sauron.getAttackTarget() == entity || entity instanceof EntityLiving && ((EntityLiving) entity).getAttackTarget() == sauron) {
					++targets;
				}
			}
		}

		return targets >= 4 && sauron.getRNG().nextInt(10) == 0;
	}

	@Override
	public boolean continueExecuting() {
		return sauron.getIsUsingMace();
	}

	@Override
	public void startExecuting() {
		attackTick = 40;
		sauron.setIsUsingMace(true);
	}

	@Override
	public void resetTask() {
		attackTick = 40;
		sauron.setIsUsingMace(false);
	}

	@Override
	public void updateTask() {
		attackTick = Math.max(attackTick - 1, 0);
		if (attackTick == 0) {
			attackTick = 40;
			LOTRItemSauronMace.useSauronMace(sauron.getEquipmentInSlot(0), world, sauron);
			sauron.setIsUsingMace(false);
		}
	}
}