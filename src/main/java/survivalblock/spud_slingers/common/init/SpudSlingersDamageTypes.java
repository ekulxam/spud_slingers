package survivalblock.spud_slingers.common.init;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import survivalblock.spud_slingers.common.SpudSlingers;

import java.util.HashMap;
import java.util.Map;

public class SpudSlingersDamageTypes {

    public static final RegistryKey<DamageType> HOT_POTATO = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, SpudSlingers.id("hot_potato"));

    public static ImmutableMap<RegistryKey<DamageType>, DamageType> asDamageTypes() {
        Map<RegistryKey<DamageType>, DamageType> damageTypes = new HashMap<>();
        damageTypes.put(HOT_POTATO, new DamageType("spud_slingers.hot_potato", Long.MAX_VALUE));
        return ImmutableMap.copyOf(damageTypes);
    }

    public static void bootstrap(Registerable<DamageType> damageTypeRegisterable) {
        for (Map.Entry<RegistryKey<DamageType>, DamageType> entry : asDamageTypes().entrySet()) {
            damageTypeRegisterable.register(entry.getKey(), entry.getValue());
        }
    }
}
