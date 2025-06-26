package survivalblock.spud_slingers.mixin.cannon.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

import static survivalblock.spud_slingers.client.SpudSlingersClient.spud_slingers$cannonPose;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin {

    @Shadow @Final public ModelPart rightArm;
    @Shadow @Final public ModelPart leftArm;
    @Shadow @Final public ModelPart head;

    @Inject(method = "positionRightArm", at = @At("HEAD"), cancellable = true)
    private void holdRightCannon(BipedEntityRenderState state, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {
        if (armPose == spud_slingers$cannonPose) {
            spud_slingers$holdCannon(this.rightArm, this.leftArm, this.head, true);
            ci.cancel();
        }
    }

    @Inject(method = "positionLeftArm", at = @At("HEAD"), cancellable = true)
    private void holdLeftCannon(BipedEntityRenderState state, BipedEntityModel.ArmPose armPose, CallbackInfo ci) {
        if (armPose == spud_slingers$cannonPose) {
            spud_slingers$holdCannon(this.rightArm, this.leftArm, this.head, false);
            ci.cancel();
        }
    }

    @Unique
    private static void spud_slingers$holdCannon(ModelPart holdingArm, ModelPart otherArm, ModelPart head, boolean rightArm) {
        ModelPart modelPart = rightArm ? holdingArm : otherArm;
        ModelPart modelPart2 = rightArm ? otherArm : holdingArm;
        modelPart.yaw = (rightArm ? -0.3F : 0.3F) + head.yaw;
        modelPart2.yaw = (rightArm ? 0.6F : -0.6F) + head.yaw;
        modelPart.pitch = (float) (-Math.PI / 2) + head.pitch + 0.1F;
        modelPart2.pitch = -1.5F + head.pitch;
    }

    @Mixin(BipedEntityModel.ArmPose.class)
    public static abstract class ArmPoseMixin {

        @Shadow @Final private static BipedEntityModel.ArmPose[] field_3404;

        static {
            ArrayList<BipedEntityModel.ArmPose> list = new ArrayList<>(Arrays.asList(field_3404));
            int size = list.size();
            spud_slingers$cannonPose = ArmPoseAccessor.spud_slingers$invokeInit("spud_slingers_cannon_pose", size, true);
            list.add(spud_slingers$cannonPose);
            field_3404 = list.toArray(new BipedEntityModel.ArmPose[size + 1]);
        }
    }
}
