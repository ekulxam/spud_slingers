package survivalblock.spud_slingers.common.init;

import com.mojang.serialization.MapCodec;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import survivalblock.spud_slingers.common.SpudSlingers;
import survivalblock.spud_slingers.common.item.HotPotatoItem;
import survivalblock.spud_slingers.common.item.PotatoCannonItem;

import java.util.function.Function;

import static net.minecraft.component.type.FoodComponents.BAKED_POTATO;

public class SpudSlingersItems {

    public static final ConsumableComponent BURNT_POTATO = ConsumableComponents.food().consumeEffect(BurnOnConsume.INSTANCE).build();

    public static final Item HOT_POTATO = registerItem("hot_potato", new Item.Settings().fireproof().maxCount(3), HotPotatoItem::new);
    public static final Item VERY_BAKED_POTATO = registerItem("very_baked_potato", new Item.Settings().food(BAKED_POTATO, BURNT_POTATO).rarity(Rarity.EPIC), Item::new);
    public static final Item POTATO_CANNON = registerItem("potato_cannon", new Item.Settings().fireproof().maxCount(1).rarity(Rarity.UNCOMMON), PotatoCannonItem::new);

    @SuppressWarnings("SameParameterValue")
    private static <T extends Item> T registerItem(String name, Item.Settings settings, Function<Item.Settings, T> itemFromSettings) {
        Identifier id = SpudSlingers.id(name);
        return Registry.register(Registries.ITEM, id, itemFromSettings.apply(settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, id))));
    }

    public static void init() {

    }

    public static class BurnOnConsume implements ConsumeEffect {

        protected BurnOnConsume() {
        }

        public static final BurnOnConsume INSTANCE = new BurnOnConsume();
        public static final MapCodec<BurnOnConsume> CODEC = MapCodec.unit(INSTANCE);
        public static final PacketCodec<RegistryByteBuf, BurnOnConsume> PACKET_CODEC = PacketCodec.unit(INSTANCE);
        public static final Type<BurnOnConsume> TYPE = Registry.register(Registries.CONSUME_EFFECT_TYPE, SpudSlingers.id("burn"), new ConsumeEffect.Type<>(CODEC, PACKET_CODEC));

        @Override
        public Type<? extends ConsumeEffect> getType() {
            return TYPE;
        }

        @Override
        public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
            if (!world.isClient()) {
                user.setOnFireForTicks(100);
            }
            return true;
        }
    }
}
