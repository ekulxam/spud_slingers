package survivalblock.spud_slingers.mixin.undroppable.client;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.spud_slingers.common.item.Undroppable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(method = "clickSlot", at = @At("HEAD"), cancellable = true)
    private void cancelClickClient(int syncId, int slotId, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        try {
            slotId = slotId >= 36 ? slotId - 36 : slotId;
            if (slotId < 0 || slotId > PlayerInventory.OFF_HAND_SLOT) {
                return;
            }
            ItemStack stack = player.getInventory().getStack(slotId);
            if (stack.getItem() instanceof Undroppable) {
                ci.cancel();
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
    }
}
