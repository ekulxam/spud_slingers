package survivalblock.spud_slingers.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.spud_slingers.common.init.SpudSlingersDamageTypes;

import java.util.concurrent.CompletableFuture;

public class SpudSlingersDamageTypeGenerator extends FabricDynamicRegistryProvider {

    public SpudSlingersDamageTypeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
        SpudSlingersDamageTypes.asDamageTypes().forEach(entries::add);
    }

    @Override
    public String getName() {
        return "Damage types";
    }
}
