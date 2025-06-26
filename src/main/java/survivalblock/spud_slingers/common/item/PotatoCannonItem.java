package survivalblock.spud_slingers.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.world.World;
import survivalblock.spud_slingers.common.entity.FlyingPotatoEntity;
import survivalblock.spud_slingers.common.init.SpudSlingersItems;

import java.util.function.Predicate;

public class PotatoCannonItem extends CrossbowItem {

    public static final Predicate<ItemStack> FLYING_POTATOES = stack -> stack.isOf(Items.POTATO) || stack.isOf(Items.POISONOUS_POTATO) || stack.isOf(SpudSlingersItems.HOT_POTATO);

    public PotatoCannonItem(Settings settings) {
        super(settings);
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return FLYING_POTATOES;
    }

    @Override
    public Predicate<ItemStack> getHeldProjectiles() {
        return this.getProjectiles();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    protected ProjectileEntity createArrowEntity(World world, LivingEntity shooter, ItemStack weaponStack, ItemStack projectileStack, boolean critical) {
        if (FLYING_POTATOES.test(projectileStack)) {
            return new FlyingPotatoEntity(shooter, world, projectileStack, weaponStack);
        }
        return super.createArrowEntity(world, shooter, weaponStack, projectileStack, critical);
    }
}
