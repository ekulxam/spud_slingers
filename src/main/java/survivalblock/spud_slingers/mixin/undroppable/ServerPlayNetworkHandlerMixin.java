package survivalblock.spud_slingers.mixin.undroppable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.spud_slingers.common.item.Undroppable;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayerEntity player;

    @Inject(method = "onClickSlot", at = @At("HEAD"), cancellable = true)
    private void cancelClickServer(ClickSlotC2SPacket packet, CallbackInfo ci) {
        int slotId = packet.slot();
        try {
            if (slotId < 0 || slotId > PlayerInventory.OFF_HAND_SLOT) {
                return;
            }
            ItemStack stack = this.player.getInventory().getStack(slotId);
            if (stack.getItem() instanceof Undroppable) {
                ci.cancel();
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
    }
}
