package dev.xkmc.fastprojectileapi.spellcircle;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class SpellRenderState extends RenderStateShard {

	public static RenderType getSpell(ResourceLocation id) {
		return RenderType.create(
				"spell_blend",
				DefaultVertexFormat.POSITION_TEX_COLOR,
				VertexFormat.Mode.QUADS, 256, true, true,
				RenderType.CompositeState.builder()
						.setShaderState(RenderStateShard.POSITION_TEX_SHADER)
						.setTextureState(new TextureStateShard(id, false, false))
						.setCullState(NO_CULL)
						.setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
						.createCompositeState(false)
		);
	}

	private SpellRenderState(String str, Runnable a, Runnable b) {
		super(str, a, b);
	}

}
