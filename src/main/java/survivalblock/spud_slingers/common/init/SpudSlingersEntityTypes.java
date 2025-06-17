package survivalblock.spud_slingers.common.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import survivalblock.spud_slingers.common.SpudSlingers;
import survivalblock.spud_slingers.common.entity.FlyingPotatoEntity;

public class SpudSlingersEntityTypes {

    public static final EntityType<FlyingPotatoEntity> FLYING_POTATO = registerEntity("flying_potato", EntityType.Builder.<FlyingPotatoEntity>create(FlyingPotatoEntity::new, SpawnGroup.MISC).dimensions(0.5f, 0.5f).makeFireImmune());

    @SuppressWarnings("SameParameterValue")
    private static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.Builder<T> builder) {
        Identifier id = SpudSlingers.id(name);
        return Registry.register(Registries.ENTITY_TYPE, id, builder.build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, id)));
    }

    public static void init() {

    }
}
