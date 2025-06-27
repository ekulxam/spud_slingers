package survivalblock.spud_slingers.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import survivalblock.spud_slingers.common.entity.FlyingPotatoEntity;
import survivalblock.spud_slingers.common.init.SpudSlingersTags;

import java.util.function.Predicate;

public class PotatoCannonItem extends CrossbowItem {

    public static final Predicate<ItemStack> FLYING_POTATOES = stack -> stack.isIn(SpudSlingersTags.POTATO_CANNON_AMMUNITION);

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
    protected ProjectileEntity createArrowEntity(World world, LivingEntity shooter, ItemStack weaponStack, ItemStack projectileStack, boolean critical) {
        if (this.getProjectiles().test(projectileStack)) {
            return new FlyingPotatoEntity(shooter, world, projectileStack, weaponStack);
        }
        return super.createArrowEntity(world, shooter, weaponStack, projectileStack, critical);
    }
}
