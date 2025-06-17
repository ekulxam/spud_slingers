package survivalblock.spud_slingers.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import survivalblock.spud_slingers.common.init.SpudSlingersDataComponentTypes;

import java.awt.Color;

import static survivalblock.spud_slingers.common.init.SpudSlingersDataComponentTypes.TICKS_UNTIL_BURN;

public class HotPotatoItem extends Item implements Undroppable {

    public static final int MAX_COUNTDOWN_TICKS = 600;
    public static final float INVERSE_MAX_COUNTDOWN_TICKS = 1f / MAX_COUNTDOWN_TICKS;

    public static final int BURN = new Color(255, 60, 0).getRGB();
    public static final int FINE = new Color(73, 255, 0).getRGB();

    public HotPotatoItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);
        if (!(entity instanceof ServerPlayerEntity player)) {
            return;
        }
        int ticks = getCountdownTicks(stack);
        if (ticks > 0) {
            stack.set(TICKS_UNTIL_BURN, ticks - 1);
        } else {
            player.damage(world, world.getDamageSources().magic(), Long.MAX_VALUE);
            if (!player.isDead()) {
                player.kill(world);
            }
            if (!player.isDead()) {
                player.remove(Entity.RemovalReason.KILLED);
                player.networkHandler.disconnect(Text.literal("hot potato strikes again"));
            }
        }
    }

    @Override
    public boolean allowContinuingBlockBreaking(PlayerEntity player, ItemStack oldStack, ItemStack newStack) {
        return true;
    }

    @Override
    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return !allowContinuingBlockBreaking(player, oldStack, newStack);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return ColorHelper.lerp(getCountdownTicks(stack) * INVERSE_MAX_COUNTDOWN_TICKS, BURN, FINE);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return MathHelper.clamp(Math.round(getCountdownTicks(stack) * 13.0F * INVERSE_MAX_COUNTDOWN_TICKS), 0, 13);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    public static int getCountdownTicks(ItemStack stack) {
        return stack.getOrDefault(TICKS_UNTIL_BURN, MAX_COUNTDOWN_TICKS);
    }
}
