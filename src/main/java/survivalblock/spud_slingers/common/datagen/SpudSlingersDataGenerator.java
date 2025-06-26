package survivalblock.spud_slingers.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.spud_slingers.common.init.SpudSlingersDamageTypes;

public class SpudSlingersDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(SpudSlingersModelGenerator::new);
		pack.addProvider(SpudSlingersEnUSLangGenerator::new);
		pack.addProvider((fabricDataOutput, completableFuture) -> new FabricDynamicRegistryProvider(fabricDataOutput, completableFuture) {
			@Override
			protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
				SpudSlingersDamageTypes.asDamageTypes().forEach(entries::add);
			}

			@Override
			public String getName() {
				return "Damage types";
			}
		});
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, SpudSlingersDamageTypes::bootstrap);
	}
}
