package survivalblock.spud_slingers.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.Nullable;
import survivalblock.spud_slingers.common.init.SpudSlingersItems;
import survivalblock.spud_slingers.common.init.SpudSlingersTags;

import java.util.concurrent.CompletableFuture;

public class SpudSlingersTagGenerator {

    public static class Item extends FabricTagProvider.ItemTagProvider {

        public Item(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(SpudSlingersTags.POTATO_CANNON_AMMUNITION)
                    .forceAddTag(SpudSlingersTags.FIERY_POTATOES)
                    .forceAddTag(SpudSlingersTags.POISON_POTATOES)
                    .add(Items.POTATO)
                    .add(Items.BAKED_POTATO);
            getOrCreateTagBuilder(SpudSlingersTags.FIERY_POTATOES)
                    .add(SpudSlingersItems.HOT_POTATO)
                    .add(SpudSlingersItems.VERY_BAKED_POTATO);
            getOrCreateTagBuilder(SpudSlingersTags.POISON_POTATOES)
                    .add(Items.POISONOUS_POTATO);
        }
    }
}
