package survivalblock.spud_slingers.common.entity;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponents;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import survivalblock.spud_slingers.common.init.SpudSlingersEntityTypes;
import survivalblock.spud_slingers.common.init.SpudSlingersItems;

public class FlyingPotatoEntity extends PersistentProjectileEntity implements FlyingItemEntity {

    public static final TrackedData<Boolean> HOT = DataTracker.registerData(FlyingPotatoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> POISON = DataTracker.registerData(FlyingPotatoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public FlyingPotatoEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings("unused")
    protected FlyingPotatoEntity(EntityType<? extends PersistentProjectileEntity> type, double x, double y, double z, World world, ItemStack stack, @Nullable ItemStack weapon) {
        super(type, x, y, z, world, stack, weapon);
        this.initTrackedDataFromStack(stack);
    }

    protected FlyingPotatoEntity(EntityType<? extends PersistentProjectileEntity> type, LivingEntity owner, World world, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(type, owner, world, stack, shotFrom);
        this.initTrackedDataFromStack(stack);
    }

    public FlyingPotatoEntity(LivingEntity owner, World world, ItemStack stack, @Nullable ItemStack shotFrom) {
        this(SpudSlingersEntityTypes.FLYING_POTATO, owner, world, stack, shotFrom);
    }

    protected void initTrackedDataFromStack(ItemStack stack) {
        this.dataTracker.set(HOT, stack.isOf(SpudSlingersItems.HOT_POTATO));
        this.dataTracker.set(POISON, stack.isOf(Items.POISONOUS_POTATO));
    }

    @Override
    public ItemStack getStack() {
        return this.getDefaultItemStack();
    }

    public boolean isHot() {
        return this.dataTracker.get(HOT);
    }

    public boolean isPoison() {
        return this.dataTracker.get(POISON);
    }

    @Override
    public boolean isOnFire() {
        return this.isHot();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(HOT, false);
        builder.add(POISON, false);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        if (this.isHot()) {
            return new ItemStack(SpudSlingersItems.HOT_POTATO);
        }
        if (this.isPoison()) {
            return new ItemStack(Items.POISONOUS_POTATO);
        }
        return new ItemStack(Items.POTATO);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() instanceof LivingEntity living) {
            this.onHit(living);
            if (!(living.getWorld() instanceof ServerWorld serverWorld)) {
                return;
            }
            ItemStack stack = this.asItemStack();
            if (this.isPoison()) {
                stack.getOrDefault(DataComponentTypes.CONSUMABLE, ConsumableComponents.POISONOUS_POTATO)
                        .onConsumeEffects()
                        .forEach(consumeEffect -> consumeEffect.onConsume(serverWorld, stack.copy(), living));
            }
            if (living instanceof PlayerEntity player) {
                player.getInventory().offerOrDrop(stack);
            } else {
                this.dropStack(serverWorld, stack);
            }
            this.discard();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!(this.getWorld() instanceof ServerWorld serverWorld)) {
            return;
        }
        this.dropStack(serverWorld, this.asItemStack());
        this.discard();
    }
}
