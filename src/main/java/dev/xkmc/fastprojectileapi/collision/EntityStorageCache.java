package dev.xkmc.fastprojectileapi.collision;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class EntityStorageCache {

	private static EntityStorageCache CACHE = null;

	public static EntityStorageCache get(Level level) {
		if (CACHE != null) {
			if (CACHE.level == level && CACHE.time == level.getGameTime()) {
				return CACHE;
			}
		}
		CACHE = new EntityStorageCache(level);
		return CACHE;
	}

	private final Level level;
	private final long time;
	private final FastMap<SectionCache> map = FastMapInit.createFastMap();

	public EntityStorageCache(Level level) {
		this.level = level;
		this.time = level.getGameTime();
	}

	private void checkSection(int x, int y, int z) {
		if (map.containsKey(x, y, z)) return;
		map.put(x, y, z, SectionCache.of(level, x, y, z));
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
