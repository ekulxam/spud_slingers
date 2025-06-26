package survivalblock.spud_slingers.mixin.immovable.client;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.spud_slingers.common.item.Immovable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(method = "clickSlot", at = @At("HEAD"), cancellable = true)
    private void cancelClickClient(int syncId, int slotId, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        try {
            ItemStack stack = player.currentScreenHandler.slots.get(slotId).getStack();
            if (stack.getItem() instanceof Immovable) {
                ci.cancel();
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
    }
}
