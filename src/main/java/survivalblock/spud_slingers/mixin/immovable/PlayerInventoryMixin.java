package survivalblock.spud_slingers.mixin.immovable;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import survivalblock.spud_slingers.common.item.Immovable;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @ModifyExpressionValue(method = "clone", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getStack(I)Lnet/minecraft/item/ItemStack;"))
    private ItemStack noTransferHotPotato(ItemStack original) {
        return original.getItem() instanceof Immovable ? ItemStack.EMPTY : original;
    }
}
