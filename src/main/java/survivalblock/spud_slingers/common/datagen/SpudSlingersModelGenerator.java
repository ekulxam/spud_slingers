package survivalblock.spud_slingers.common.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ItemModels;
import net.minecraft.client.data.ModelIds;
import net.minecraft.client.data.Models;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.property.numeric.CrossbowPullProperty;
import net.minecraft.client.render.item.property.select.ChargeTypeProperty;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import survivalblock.spud_slingers.common.init.SpudSlingersItems;

public class SpudSlingersModelGenerator extends FabricModelProvider {

    public SpudSlingersModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        Identifier hotPotato = itemModelGenerator.uploadTwoLayers(SpudSlingersItems.HOT_POTATO, Identifier.ofVanilla("block/fire_0"), Identifier.ofVanilla("item/potato"));
        itemModelGenerator.output.accept(SpudSlingersItems.HOT_POTATO, ItemModels.basic(hotPotato));
        itemModelGenerator.register(SpudSlingersItems.VERY_BAKED_POTATO, Models.GENERATED);
        registerCannon(itemModelGenerator, SpudSlingersItems.POTATO_CANNON);
    }

    @SuppressWarnings("SameParameterValue")
    private static void registerCannon(ItemModelGenerator generator, Item item) {
        ItemModel.Unbaked unbaked = ItemModels.basic(ModelIds.getItemModelId(item));
        generator.output
                .accept(
                        item,
                        ItemModels.select(
                                new ChargeTypeProperty(),
                                ItemModels.condition(
                                        ItemModels.usingItemProperty(),
                                        ItemModels.rangeDispatch(
                                                new CrossbowPullProperty(), unbaked, ItemModels.rangeDispatchEntry(unbaked, 0.58F), ItemModels.rangeDispatchEntry(unbaked, 1.0F)
                                        ),
                                        unbaked
                                ),
                                ItemModels.switchCase(CrossbowItem.ChargeType.ARROW, unbaked)
                        )
                );
    }
}
