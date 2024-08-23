package dev.xkmc.fastprojectileapi.collision;

import dev.xkmc.fastprojectileapi.entity.SimplifiedEntity;
import net.minecraft.world.entity.Entity;

public enum ChoiceType {
	PICKABLE, NOPICK, SIMPLIFIED;

	public boolean test(Entity e) {
		if (e.isPickable()) {
			return this == PICKABLE;
		}
		if (e instanceof SimplifiedEntity) {
			return this == SIMPLIFIED;
		}
		return this == NOPICK;
	}
}
