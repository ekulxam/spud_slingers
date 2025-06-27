package survivalblock.spud_slingers.mixin.cannon.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.spud_slingers.common.init.SpudSlingersItems;

@Debug(export = true)
@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

    @WrapOperation(method = "getArmPose(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private static boolean pretendCannonsAreCrossbows(ItemStack instance, Item item, Operation<Boolean> original) {
        return instance.isOf(item) || instance.isOf(SpudSlingersItems.POTATO_CANNON);
    }
}
