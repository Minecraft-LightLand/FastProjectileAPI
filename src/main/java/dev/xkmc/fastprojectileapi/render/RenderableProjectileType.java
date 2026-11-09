package dev.xkmc.fastprojectileapi.render;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.xkmc.fastprojectileapi.entity.SimplifiedProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public interface RenderableProjectileType<T extends RenderableProjectileType<T, I>, I> extends Comparable<RenderableProjectileType<?, ?>> {

	void start(MultiBufferSource buffer, List<I> list);

	void create(Consumer<I> holder, ProjectileRenderer<?> r, SimplifiedProjectile e, PoseStack pose, float pTick);

	@Override
	default int compareTo(@NotNull RenderableProjectileType<?, ?> o) {
		if (this == o) return 0;
		int c = Integer.compare(order(), o.order());
		return c != 0 ? c : Integer.compare(hashCode(), o.hashCode());
	}

	default int order() {
		return 0;
	}

}