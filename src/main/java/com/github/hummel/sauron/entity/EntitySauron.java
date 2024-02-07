package com.github.hummel.sauron.entity;

import com.github.hummel.sauron.entity.ai.EntityAISauronUseMace;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySauron extends LOTREntityNPC {
	public EntitySauron(World world) {
		super(world);
		setSize(0.8F, 2.2F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAISauronUseMace(this));
		tasks.addTask(2, new LOTREntityAIAttackOnCollide(this, 2.0, false));
		tasks.addTask(4, new EntityAIWander(this, 1.0));
		tasks.addTask(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 24.0F, 0.02F));
		tasks.addTask(5, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 16.0F, 0.02F));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityLiving.class, 12.0F, 0.02F));
		tasks.addTask(7, new EntityAILookIdle(this));
		addTargetTasks(true);
		spawnsInDarkness = true;
		isImmuneToFire = true;
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(17, (byte) 0);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
		getEntityAttribute(npcAttackDamage).setBaseValue(6.0);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData iEntityLivingData = super.onSpawnWithEgg(data);
		setCurrentItemOrArmor(0, new ItemStack(LOTRMod.sauronMace));
		return iEntityLivingData;
	}

	@Override
	public LOTRFaction getFaction() {
		return LOTRFaction.MORDOR;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (getHealth() < getMaxHealth() && ticksExisted % 10 == 0) {
			heal(1.0F);
		}
		if (getIsUsingMace() && worldObj.isRemote) {
			for (int i = 0; i < 6; ++i) {
				double d = posX - 2.0 + rand.nextFloat() * 4.0F;
				double d1 = posY + rand.nextFloat() * 3.0F;
				double d2 = posZ - 2.0 + rand.nextFloat() * 4.0F;
				double d3 = (posX - d) / 8.0;
				double d4 = (posY + 0.5 - d1) / 8.0;
				double d5 = (posZ - d2) / 8.0;
				double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
				double d7 = 1.0 - d6;
				double d8 = 0.0;
				double d9 = 0.0;
				double d10 = 0.0;
				if (d7 > 0.0) {
					d7 *= d7;
					d8 += d3 / d6 * d7 * 0.2;
					d9 += d4 / d6 * d7 * 0.2;
					d10 += d5 / d6 * d7 * 0.2;
				}
				worldObj.spawnParticle("smoke", d, d1, d2, d8, d9, d10);
			}
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		return damagesource != DamageSource.fall && super.attackEntityFrom(damagesource, f);
	}

	@Override
	public int getExperiencePoints(EntityPlayer entityplayer) {
		return 15 + rand.nextInt(10);
	}

	@Override
	public void dropNPCEquipment(boolean flag, int i) {
		if (flag && rand.nextInt(3) == 0) {
			ItemStack heldItem = getHeldItem();
			if (heldItem != null) {
				entityDropItem(heldItem, 0.0F);
				setCurrentItemOrArmor(0, null);
			}
		}

		super.dropNPCEquipment(flag, i);
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		int exRange;
		if (worldObj.isRemote) {
			for (exRange = 0; exRange < 100; ++exRange) {
				double d = posX + width * MathHelper.randomFloatClamp(rand, -1.0F, 1.0F);
				double d1 = posY + height * MathHelper.randomFloatClamp(rand, 0.0F, 1.0F);
				double d2 = posZ + width * MathHelper.randomFloatClamp(rand, -1.0F, 1.0F);
				double d3 = rand.nextGaussian() * 0.03;
				double d4 = rand.nextGaussian() * 0.03;
				double d5 = rand.nextGaussian() * 0.03;
				if (rand.nextInt(3) == 0) {
					worldObj.spawnParticle("explode", d, d1, d2, d3, d4, d5);
				} else {
					worldObj.spawnParticle("flame", d, d1, d2, d3, d4, d5);
				}
			}
		} else {
			exRange = 8;
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(posY);
			int k = MathHelper.floor_double(posZ);

			for (int i1 = i - exRange; i1 <= i + exRange; ++i1) {
				for (int j1 = j - exRange; j1 <= j + exRange; ++j1) {
					for (int k1 = k - exRange; k1 <= k + exRange; ++k1) {
						Block block = worldObj.getBlock(i1, j1, k1);
						if (block.getMaterial() == Material.fire) {
							worldObj.setBlockToAir(i1, j1, k1);
						}
					}
				}
			}
		}

		super.onDeath(damagesource);
		playSound(getHurtSound(), getSoundVolume() * 2.0F, getSoundPitch() * 0.75F);
	}

	public boolean getIsUsingMace() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	public void setIsUsingMace(boolean flag) {
		dataWatcher.updateObject(17, (byte) (flag ? 1 : 0));
	}
}