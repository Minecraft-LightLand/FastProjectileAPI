package dev.xkmc.fastprojectileapi.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.level.Level;

public interface ClientObjectCache {

	void tick();

	void renderAll(Camera camera, Frustum frustum, PoseStack pose, DeltaTracker pTick, MultiBufferSource.BufferSource buffer);

	interface Provider {

		ClientObjectCache get(Level level);

	}

}
