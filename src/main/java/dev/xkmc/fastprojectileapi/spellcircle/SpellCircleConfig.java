package dev.xkmc.fastprojectileapi.spellcircle;

import dev.xkmc.fastprojectileapi.init.FastProjectileAPI;
import dev.xkmc.l2core.serial.config.BaseConfig;
import dev.xkmc.l2core.serial.config.CollectType;
import dev.xkmc.l2core.serial.config.ConfigCollect;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

@SerialClass
public class SpellCircleConfig extends BaseConfig {

	@Nullable
	public static SpellComponent getFromConfig(ResourceLocation s) {
		return FastProjectileAPI.SPELL.getMerged().map.get(s.toString());
	}

	@ConfigCollect(CollectType.MAP_OVERWRITE)
	@SerialField
	public HashMap<String, SpellComponent> map = new HashMap<>();

}
