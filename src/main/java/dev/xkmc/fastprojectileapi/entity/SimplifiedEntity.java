package dev.xkmc.fastprojectileapi.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;

public abstract class SimplifiedEntity extends Entity {

	public SimplifiedEntity(EntityType<?> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	public void tick() {
		baseTick();
	}

	@Override
	public void baseTick() {
		this.walkDistO = this.walkDist;
		this.xRotO = this.getXRot();
		this.yRotO = this.getYRot();
		this.checkBelowWorld();
		this.firstTick = false;
	}

	protected boolean updateInWaterStateAndDoFluidPushing() {
		return false;
	}

	protected void doWaterSplashEffect() {
	}

	@Override
	public boolean canSpawnSprintParticle() {
		return false;
	}

	@Override
	protected void tryCheckInsideBlocks() {
	}

	@Override
	protected void checkInsideBlocks() {
	}

	@Override
	public int getRemainingFireTicks() {
		return 0;
	}

	@Override
	public void setRemainingFireTicks(int pRemainingFireTicks) {
	}

	@Override
	public void clearFire() {
	}

	public PushReaction getPistonPushReaction() {
		return PushReaction.IGNORE;
	}

	public boolean mayInteract(Level pLevel, BlockPos pPos) {
		return false;
	}

	private int typeId = -1;

	public int getTypeId() {
		if (typeId < 0) {
			typeId = BuiltInRegistries.ENTITY_TYPE.getId(getType());
		}
		return typeId;
	}

	@Override
	public void igniteForTicks(int pTicks) {
	}

	@Override
	public void extinguishFire() {
	}

	@Override
	public void push(Entity pEntity) {
	}

	@Override
	public void push(Vec3 p_347665_) {
	}

	@Override
	public void push(double pX, double pY, double pZ) {
	}

	@Override
	public boolean isInvulnerableTo(DamageSource pSource) {
		return isRemoved();
	}

}
