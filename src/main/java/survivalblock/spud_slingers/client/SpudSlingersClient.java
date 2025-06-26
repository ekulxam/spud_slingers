package survivalblock.spud_slingers.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import survivalblock.spud_slingers.common.init.SpudSlingersEntityTypes;
import survivalblock.spud_slingers.common.init.SpudSlingersItems;

import java.util.function.Supplier;

public class SpudSlingersClient implements ClientModInitializer {

    public static BipedEntityModel.ArmPose spud_slingers$cannonPose;

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(SpudSlingersEntityTypes.FLYING_POTATO, ctx -> new FlyingItemEntityRenderer<>(ctx, 1, true));

        ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
            if (stack.isOf(SpudSlingersItems.VERY_BAKED_POTATO)) {
                Supplier<MutableText> quotationMark = () -> Text.literal("\"").formatted(Formatting.WHITE); // I hope this is good enough for most languages
                lines.add(quotationMark.get().append(Text.translatable("item.spud_slingers.very_baked_potato.lore.0").formatted(Formatting.WHITE, Formatting.ITALIC)).append(quotationMark.get()));
                lines.add(Text.translatable("item.spud_slingers.very_baked_potato.lore.1").withColor(16674374)); // 254, 110, 70
            }
        });
    }
}
