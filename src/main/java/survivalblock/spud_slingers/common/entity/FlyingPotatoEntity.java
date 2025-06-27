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
import survivalblock.spud_slingers.common.init.SpudSlingersTags;

import java.util.Objects;
import java.util.function.Supplier;

public class FlyingPotatoEntity extends PersistentProjectileEntity implements FlyingItemEntity {

    public static final Supplier<ItemStack> DEFAULT_STACK_SUPPLIER = Items.POTATO::getDefaultStack;

    public static final TrackedData<ItemStack> RENDER_STACK
            = DataTracker.registerData(FlyingPotatoEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

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
        this.dataTracker.set(RENDER_STACK, stack);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient()) {
            ItemStack stack = this.getItemStack();
            if(!ItemStack.areItemsAndComponentsEqual(this.dataTracker.get(RENDER_STACK), this.getItemStack())) {
                initTrackedDataFromStack(stack);
            }
        }
    }

    @Override
    public ItemStack getStack() {
        return this.getDefaultItemStack();
    }

    @Override
    public boolean isOnFire() {
        return this.dataTracker.get(RENDER_STACK).isIn(SpudSlingersTags.FIERY_POTATOES);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(RENDER_STACK, DEFAULT_STACK_SUPPLIER.get());
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return this.dataTracker.get(RENDER_STACK).copy();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!(entityHitResult.getEntity() instanceof LivingEntity living)) {
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                this.dropPotato(serverWorld);
                this.discard();
            }
            return;
        }
        if (living.getType() == EntityType.ENDERMAN) {
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                this.dropPotato(serverWorld);
                this.discard();
            }
            return;
        }
        this.onHit(living);
        if (!(living.getWorld() instanceof ServerWorld serverWorld)) {
            return;
        }
        ItemStack stack = this.asItemStack();
        if (stack.isIn(SpudSlingersTags.POISON_POTATOES)) {
            stack.getOrDefault(DataComponentTypes.CONSUMABLE, ConsumableComponents.POISONOUS_POTATO)
                    .onConsumeEffects()
                    .forEach(consumeEffect -> consumeEffect.onConsume(serverWorld, stack.copy(), living));
        }
        if (this.isOnFire()) {
            living.setOnFireForTicks(100);
        }
        if (living instanceof PlayerEntity player) {
            player.getInventory().offerOrDrop(stack);
        } else {
            this.dropPotato(serverWorld);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!(this.getWorld() instanceof ServerWorld serverWorld)) {
            return;
        }
        this.dropPotato(serverWorld);
        this.discard();
    }

    protected void dropPotato(ServerWorld serverWorld) {
        if (PickupPermission.ALLOWED == this.pickupType) {
            this.dropStack(serverWorld, this.asItemStack());
        }
    }
}
