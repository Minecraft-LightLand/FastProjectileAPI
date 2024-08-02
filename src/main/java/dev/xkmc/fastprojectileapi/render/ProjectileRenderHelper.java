package dev.xkmc.fastprojectileapi.render;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import dev.xkmc.fastprojectileapi.FastProjectileAPI;
import dev.xkmc.l2serial.util.Wrappers;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.util.Map;
import java.util.Set;


@EventBusSubscriber(value = Dist.CLIENT, modid = FastProjectileAPI.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ProjectileRenderHelper {

	private static final Map<RenderableProjectileType<?, ?>, Set<?>> MAP = Maps.newConcurrentMap();

	public static <T extends RenderableProjectileType<T, I>, I> Set<I> setOf(RenderableProjectileType<T, I> key) {
		return Wrappers.cast(MAP.computeIfAbsent(key, l -> Sets.newConcurrentHashSet()));
	}

	public static <T extends RenderableProjectileType<T, I>, I> void add(RenderableProjectileType<T, I> key, I ins) {
		setOf(key).add(ins);
	}

	@SubscribeEvent
	public static void renderLate(RenderLevelStageEvent event) {
		if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) return;
		var buffer = Minecraft.getInstance().renderBuffers().bufferSource();
		for (var ent : MAP.entrySet()) {
			ent.getKey().start(buffer, Wrappers.cast(ent.getValue()));
		}
		buffer.endLastBatch();
		MAP.clear();

	}
}
