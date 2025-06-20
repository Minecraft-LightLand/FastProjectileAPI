package dev.xkmc.fastprojectileapi.render;

import dev.xkmc.fastprojectileapi.FastProjectileAPI;
import dev.xkmc.l2serial.util.Wrappers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EventBusSubscriber(value = Dist.CLIENT, modid = FastProjectileAPI.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ProjectileRenderHelper {

	public static final List<ClientObjectCache.Provider> LIST = new ArrayList<>();

	private static RenderQueue QUEUE;

	public static synchronized void setup() {
		ProjTypeHolder.setup();
		QUEUE = new RenderQueue();
	}

	public static <T extends RenderableProjectileType<T, I>, I> Collection<I> setOf(ProjTypeHolder<T, I> key) {
		return QUEUE.setOf(key);
	}

	public static <T extends RenderableProjectileType<T, I>, I> void add(ProjTypeHolder<T, I> key, I ins) {
		setOf(key).add(ins);
	}

	@SubscribeEvent
	public static void clientTick(LevelTickEvent.Post event) {
		var level = Minecraft.getInstance().level;
		if (level != event.getLevel()) return;
		for (var e : LIST) {
			e.get(level).tick();
		}
	}

	@SubscribeEvent
	public static void renderLate(RenderLevelStageEvent event) {
		var level = Minecraft.getInstance().level;
		if (level == null) return;
		if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
			var buffer = Minecraft.getInstance().renderBuffers().bufferSource();
			for (var e : LIST) {
				e.get(level).renderAll(event.getCamera(), event.getFrustum(), event.getPoseStack(), event.getPartialTick(), buffer);
			}
			QUEUE.flush(buffer);
			buffer.endLastBatch();
		}
		if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
			var buffer = Minecraft.getInstance().renderBuffers().bufferSource();
			QUEUE.flush(buffer);
			buffer.endLastBatch();
		}
	}

	private static class RenderQueue {

		private final ArrayList<?>[] lists = new ArrayList<?>[ProjTypeHolder.HOLDERS.size()];

		public <I> ArrayList<I> setOf(ProjTypeHolder<?, I> key) {
			if (lists[key.index] == null) {
				lists[key.index] = new ArrayList<>();
			}
			return Wrappers.cast(lists[key.index]);
		}

		public void flush(MultiBufferSource.BufferSource buffer) {
			int n = lists.length;
			for (int i = 0; i < n; i++) {
				var list = lists[i];
				lists[i] = null;
				if (list != null) {
					ProjTypeHolder.HOLDERS.get(i).type.start(buffer, Wrappers.cast(list));
				}
			}
		}

	}

}
