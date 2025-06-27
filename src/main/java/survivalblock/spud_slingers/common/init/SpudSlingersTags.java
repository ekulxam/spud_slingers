package survivalblock.spud_slingers.common.init;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import survivalblock.spud_slingers.common.SpudSlingers;

public class SpudSlingersTags {

    public static final TagKey<Item> POTATO_CANNON_AMMUNITION = item("potato_cannon_ammunition");
    public static final TagKey<Item> FIERY_POTATOES = item("fiery_potatoes");
    public static final TagKey<Item> POISON_POTATOES = item("poison_potatoes");

    private static TagKey<Item> item(String path) {
        return TagKey.of(RegistryKeys.ITEM, SpudSlingers.id(path));
    }
}
