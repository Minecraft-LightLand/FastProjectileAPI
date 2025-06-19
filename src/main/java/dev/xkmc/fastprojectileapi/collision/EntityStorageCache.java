package dev.xkmc.fastprojectileapi.collision;

import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;

public class EntityStorageCache implements IEntityCache {

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

	@Override
	public SectionCache get(int x, int y, int z) {
		var ans = map.get(x, y, z);
		if (ans == null) {
			ans = SectionCache.of(level, x, y, z, type);
			map.put(x, y, z, ans);
		}
		return ans;
	}

}
