package dev.xkmc.fastprojectileapi.init;

import dev.xkmc.l2core.util.ConfigInit;
import net.neoforged.neoforge.common.ModConfigSpec;

public class FPConfig {

	public static class Client extends ConfigInit {

		public final ModConfigSpec.BooleanValue laserRenderAdditive;
		public final ModConfigSpec.BooleanValue laserRenderInverted;
		public final ModConfigSpec.DoubleValue laserTransparency;

		Client(Builder builder) {
			markL2();
			laserRenderAdditive = builder.define("laserRenderAdditive", true);
			laserRenderInverted = builder.define("laserRenderInverted", true);
			laserTransparency = builder.defineInRange("laserTransparency", 0.5, 0, 1);
		}

	}

	public static final Client CLIENT = FastProjectileAPI.REGISTRATE.registerClient(Client::new);

}
