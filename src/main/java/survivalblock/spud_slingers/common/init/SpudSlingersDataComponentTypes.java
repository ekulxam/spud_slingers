package survivalblock.spud_slingers.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import survivalblock.spud_slingers.common.SpudSlingers;

public class SpudSlingersDataComponentTypes {

    public static final ComponentType<Integer> TICKS_UNTIL_BURN = register("ticks_until_burn", ComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT));

    private static <T> ComponentType<T> register(String name, ComponentType.Builder<T> builder) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, SpudSlingers.id(name), builder.build());
    }

    public static void init() {}

}
