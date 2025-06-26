package survivalblock.spud_slingers.mixin.immovable.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.spud_slingers.common.item.Immovable;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin extends Screen {

    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @WrapWithCondition(method = "keyPressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V", ordinal = 0), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;dropKey:Lnet/minecraft/client/option/KeyBinding;")))
    private boolean noDropClientScreen(HandledScreen instance, Slot slot, int slotId, int button, SlotActionType actionType) {
        return slot == null || !(slot.getStack().getItem() instanceof Immovable);
    }

    @Inject(method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V", at = @At("HEAD"), cancellable = true)
    private void cancelMouseClickClient(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        if (slot != null) {
            if (slot.getStack().getItem() instanceof Immovable) {
                ci.cancel();
            }
        }
        if (actionType != SlotActionType.SWAP) {
            return;
        }
        if (this.client == null || this.client.player == null) {
            return;
        }
        if (this.client.player.getInventory().getStack(button).getItem() instanceof Immovable) {
            ci.cancel();
        }
    }
}
