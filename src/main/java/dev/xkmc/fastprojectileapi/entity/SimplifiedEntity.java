package dev.xkmc.fastprojectileapi.entity;

import net.minecraft.core.BlockPos;
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

	@Override
	public void baseTick() {
		this.level().getProfiler().push("entityBaseTick");
		if (this.boardingCooldown > 0) {
			--this.boardingCooldown;
		}
		this.walkDistO = this.walkDist;
		this.xRotO = this.getXRot();
		this.yRotO = this.getYRot();
		handlePortal();
		this.checkBelowWorld();
		this.firstTick = false;
		this.level().getProfiler().pop();
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
