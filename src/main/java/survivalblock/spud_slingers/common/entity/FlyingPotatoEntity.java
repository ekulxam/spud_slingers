package survivalblock.spud_slingers.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import survivalblock.spud_slingers.common.init.SpudSlingersItems;

public class FlyingPotatoEntity extends PersistentProjectileEntity implements FlyingItemEntity {

    public static final TrackedData<Boolean> HOT = DataTracker.registerData(FlyingPotatoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public FlyingPotatoEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    protected FlyingPotatoEntity(EntityType<? extends PersistentProjectileEntity> type, double x, double y, double z, World world, ItemStack stack, @Nullable ItemStack weapon) {
        super(type, x, y, z, world, stack, weapon);
    }

    protected FlyingPotatoEntity(EntityType<? extends PersistentProjectileEntity> type, LivingEntity owner, World world, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(type, owner, world, stack, shotFrom);
    }

    @Override
    public ItemStack getStack() {
        return this.asItemStack();
    }

    public boolean isHot() {
        return this.dataTracker.get(HOT);
    }

    @Override
    public boolean isOnFire() {
        return this.isHot();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(HOT, false);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return this.isHot() ? new ItemStack(SpudSlingersItems.HOT_POTATO) : new ItemStack(Items.POTATO);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() instanceof LivingEntity living) {
            this.onHit(living);
            if (living instanceof PlayerEntity player) {
                player.getInventory().offerOrDrop(this.asItemStack());
            }
        }
    }
}
