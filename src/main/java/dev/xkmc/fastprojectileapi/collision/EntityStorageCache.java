package dev.xkmc.fastprojectileapi.collision;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class EntityStorageCache {

	private static final Mutable<EntityStorageCache> CACHE = new MutableObject<>();
	private static final Mutable<EntityStorageCache> CLIENT = new MutableObject<>();
	private static final Mutable<EntityStorageCache> NOPICK = new MutableObject<>();
	private static final Mutable<EntityStorageCache> NOPICKC = new MutableObject<>();
	private static final Mutable<EntityStorageCache> SIMPLIFIED = new MutableObject<>();
	private static final Mutable<EntityStorageCache> SIMPLIFIEDC = new MutableObject<>();

	public static EntityStorageCache get(Level level) {
		return getInternal(level, ChoiceType.PICKABLE, level.isClientSide() ? CLIENT : CACHE);
	}

	public static EntityStorageCache get(Level level, ChoiceType type) {
		var holder = switch (type) {
			case PICKABLE -> level.isClientSide() ? CLIENT : CACHE;
			case NOPICK -> level.isClientSide() ? NOPICKC : NOPICK;
			case SIMPLIFIED -> level.isClientSide() ? SIMPLIFIEDC : SIMPLIFIED;
		};
		return getInternal(level, type, holder);
	}

	public static EntityStorageCache getInternal(Level level, ChoiceType type, Mutable<EntityStorageCache> holder) {
		var val = holder.getValue();
		if (val != null) {
			if (val.level == level && val.time == level.getGameTime()) {
				return val;
			}
		}
		val = new EntityStorageCache(level, type);
		holder.setValue(val);
		return val;
	}

	private final Level level;
	private final long time;
	private final FastMap<SectionCache> map = FastMapInit.createFastMap();
	private final ChoiceType type;

	public EntityStorageCache(Level level, ChoiceType type) {
		this.level = level;
		this.time = level.getGameTime();
		this.type = type;
	}

	private void checkSection(int x, int y, int z) {
		if (map.containsKey(x, y, z)) return;
		map.put(x, y, z, SectionCache.of(level, x, y, z, type));
	}

	public Iterable<Entity> foreach(AABB aabb, Predicate<Entity> filter) {
		int x0 = (((int) aabb.minX) >> 4) - 1;
		int y0 = (((int) aabb.minY) >> 4) - 1;
		int z0 = (((int) aabb.minZ) >> 4) - 1;
		int x1 = (((int) aabb.maxX) >> 4) + 1;
		int y1 = (((int) aabb.maxY) >> 4) + 1;
		int z1 = (((int) aabb.maxZ) >> 4) + 1;
		List<Entity> list = new ArrayList<>();
		for (int x = x0; x <= x1; x++) {
			for (int y = y0; y <= y1; y++) {
				for (int z = z0; z <= z1; z++) {
					checkSection(x, y, z);
					for (var e : map.get(x, y, z).intersect(aabb)) {
						if (aabb.intersects(e.getBoundingBox()) && filter.test(e)) {
							list.add(e);
						}
					}
				}
			}
		}
		return list;
	}

}
