package survivalblock.spud_slingers.common.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ItemModels;
import net.minecraft.client.data.Model;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TextureKey;
import net.minecraft.util.Identifier;
import survivalblock.spud_slingers.common.init.SpudSlingersItems;

import java.util.Optional;

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
    }

    private static Model item(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.ofVanilla("item/" + parent)), Optional.empty(), requiredTextureKeys);
    }
}
