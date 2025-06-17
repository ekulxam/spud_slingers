package survivalblock.spud_slingers.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import survivalblock.spud_slingers.common.init.SpudSlingersEntityTypes;

public class SpudSlingersClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(SpudSlingersEntityTypes.FLYING_POTATO, ctx -> new FlyingItemEntityRenderer<>(ctx, 1, true));


    }
}
