package survivalblock.spud_slingers.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmokingRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.item.Items.BAKED_POTATO;
import static survivalblock.spud_slingers.common.init.SpudSlingersItems.HOT_POTATO;

public class SpudSlingersRecipeGenerator extends FabricRecipeProvider {

    public SpudSlingersRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                int cookingTime = 200;
                final float experience = 1F;
                CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItem(BAKED_POTATO), RecipeCategory.FOOD, HOT_POTATO, experience, cookingTime)
                        .criterion(hasItem(BAKED_POTATO), this.conditionsFromItem(BAKED_POTATO))
                        .offerTo(this.exporter);
                this.offerFoodCookingRecipe("smoking", RecipeSerializer.SMOKING, SmokingRecipe::new, cookingTime, BAKED_POTATO, HOT_POTATO, experience);
                this.offerFoodCookingRecipe("campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, cookingTime * 3, BAKED_POTATO, HOT_POTATO, experience);
            }
        };
    }

    @Override
    public String getName() {
        return "Recipes";
    }
}
