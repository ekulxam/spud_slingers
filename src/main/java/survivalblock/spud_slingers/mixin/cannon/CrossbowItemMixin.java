package survivalblock.spud_slingers.mixin.cannon;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    @Inject(method = "shootAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;)V"))
    private void afterShotServer(World world, LivingEntity shooter, Hand hand, ItemStack stack, float speed, float divergence, LivingEntity target, CallbackInfo ci, @Local ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.getItemCooldownManager().set(stack, 3);
    }
}
