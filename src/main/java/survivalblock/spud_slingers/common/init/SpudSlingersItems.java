package survivalblock.spud_slingers.common.init;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import survivalblock.spud_slingers.common.SpudSlingers;
import survivalblock.spud_slingers.common.item.HotPotatoItem;

import java.util.function.Function;

public class SpudSlingersItems {

    public static final Item HOT_POTATO = registerItem("hot_potato", new Item.Settings().fireproof(), HotPotatoItem::new);

    @SuppressWarnings("SameParameterValue")
    private static <T extends Item> T registerItem(String name, Item.Settings settings, Function<Item.Settings, T> itemFromSettings) {
        Identifier id = SpudSlingers.id(name);
        return Registry.register(Registries.ITEM, id, itemFromSettings.apply(settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, id))));
    }

    public static void init() {

    }
}
