package survivalblock.spud_slingers.mixin.undroppable;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.spud_slingers.common.item.Undroppable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @SuppressWarnings("ConstantValue")
    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("HEAD"), cancellable = true)
    private void noDropLiving(ItemStack stack, boolean dropAtSelf, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        if (stack.getItem() instanceof Undroppable && (LivingEntity) (Object) this instanceof PlayerEntity) {
            cir.setReturnValue(null);
        }
    }
}
