package survivalblock.spud_slingers.mixin.cannon.client;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

// this is a very bad idea
@Mixin(BipedEntityModel.ArmPose.class)
public interface ArmPoseAccessor {

    // first two parameters are synthetic
    @Invoker("<init>")
    static BipedEntityModel.ArmPose spud_slingers$invokeInit(String name, int id, final boolean twoHanded) {
        throw new UnsupportedOperationException();
    }
}