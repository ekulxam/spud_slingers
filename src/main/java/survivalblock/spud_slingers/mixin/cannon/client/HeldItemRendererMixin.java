package survivalblock.spud_slingers.mixin.cannon.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.spud_slingers.common.init.SpudSlingersItems;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @WrapOperation(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getUseAction()Lnet/minecraft/item/consume/UseAction;"))
    private UseAction pretendCannonsAreBowsInFirstPerson(ItemStack instance, Operation<UseAction> original) {
        if (instance.isOf(SpudSlingersItems.POTATO_CANNON)) {
            return UseAction.BOW;
        }
        return original.call(instance);
    }
}
