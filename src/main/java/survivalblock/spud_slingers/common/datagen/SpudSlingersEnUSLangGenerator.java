package survivalblock.spud_slingers.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.spud_slingers.common.init.SpudSlingersEntityTypes;
import survivalblock.spud_slingers.common.init.SpudSlingersItems;

import java.util.concurrent.CompletableFuture;

public class SpudSlingersEnUSLangGenerator extends FabricLanguageProvider {

    protected SpudSlingersEnUSLangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(SpudSlingersItems.HOT_POTATO, "Hot Potato");
        translationBuilder.add(SpudSlingersItems.VERY_BAKED_POTATO, "Very Baked Potato");
        translationBuilder.add("item.spud_slingers.very_baked_potato.lore.0", "something tells me someone died for this");
        translationBuilder.add("item.spud_slingers.very_baked_potato.lore.1", "- ActiveRadar, 2025");
        translationBuilder.add(SpudSlingersItems.POTATO_CANNON, "Potato Cannon");
        translationBuilder.add(SpudSlingersEntityTypes.FLYING_POTATO, "Flying Potato");
        translationBuilder.add("death.attack.spud_slingers.hot_potato", "%1$s was cooked to death by a hot potato (oh no you died)");
    }
}
