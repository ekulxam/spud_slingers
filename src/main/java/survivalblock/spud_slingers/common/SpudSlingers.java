package survivalblock.spud_slingers.common;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.spud_slingers.common.init.SpudSlingersDataComponentTypes;
import survivalblock.spud_slingers.common.init.SpudSlingersEntityTypes;
import survivalblock.spud_slingers.common.init.SpudSlingersItems;

public class SpudSlingers implements ModInitializer {
	public static final String MOD_ID = "spud_slingers";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		SpudSlingersDataComponentTypes.init();
		SpudSlingersItems.init();
		SpudSlingersEntityTypes.init();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}