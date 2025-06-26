package survivalblock.spud_slingers.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import survivalblock.spud_slingers.common.init.SpudSlingersDamageTypes;
import survivalblock.spud_slingers.common.init.SpudSlingersItems;

import java.awt.Color;

import static survivalblock.spud_slingers.common.init.SpudSlingersDataComponentTypes.TICKS_UNTIL_BURN;

public class HotPotatoItem extends Item implements Immovable {

    public static final int MAX_COUNTDOWN_TICKS = 400;
    public static final float INVERSE_MAX_COUNTDOWN_TICKS = 1f / MAX_COUNTDOWN_TICKS;

    public static final int BURN = new Color(255, 60, 0).getRGB();
    public static final int FINE = new Color(73, 255, 0).getRGB();

    public static final int WARN_0 = new Color(255, 0, 0).getRGB();
    public static final int WARN_1 = new Color(255, 153, 0).getRGB();

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
            int count = stack.getCount();
            stack.setCount(0);
            player.getInventory().offerOrDrop(new ItemStack(SpudSlingersItems.VERY_BAKED_POTATO, count));
            RegistryEntry.Reference<DamageType> damageTypeReference = world.getRegistryManager().getOrThrow(RegistryKeys.DAMAGE_TYPE).getOrThrow(SpudSlingersDamageTypes.HOT_POTATO);
            player.damage(world, new DamageSource(damageTypeReference), Long.MAX_VALUE);
            MinecraftServer server = player.getServer();
            if (server != null) {
                server.send(new ServerTask(server.getTicks(), () -> {
                    if (!player.isDead()) {
                        // assume they cheated death somehow
                        player.remove(Entity.RemovalReason.KILLED);
                        player.networkHandler.disconnect(Text.literal("hot potato strikes again"));
                    }
                }));
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

    @Override
    public Text getName(ItemStack stack) {
        Text text = super.getName(stack);
        if (!Text.translatable("item.spud_slingers.hot_potato").equals(text)) {
            return text;
        }
        float delta = ((float) Math.sin(Util.getMeasuringTimeMs() * 0.008) + 1) * 0.5f;
        return text.copy().withColor(ColorHelper.lerp(delta, WARN_0, WARN_1));
    }
}
