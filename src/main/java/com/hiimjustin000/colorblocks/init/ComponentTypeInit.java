package com.hiimjustin000.colorblocks.init;

import com.hiimjustin000.colorblocks.ColorBlocks;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.dynamic.Codecs;

import java.util.List;
import java.util.function.UnaryOperator;

public class ComponentTypeInit
{
    public static ComponentType<Integer> COLOR = register("color", builder ->
            builder.codec(Codecs.rangedInt(0, 16777215)).packetCodec(PacketCodecs.VAR_INT));
    public static ComponentType<List<Integer>> COLORS = register("colors", builder ->
            builder.codec(Codecs.rangedInt(0, 16777215).listOf()).packetCodec(PacketCodecs.VAR_INT.collect(PacketCodecs.toList())));
    public static ComponentType<Integer> SPEED = register("speed", builder ->
            builder.codec(Codecs.rangedInt(1, 20)).packetCodec(PacketCodecs.VAR_INT));

    public static <T> ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> operator)
    {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, ColorBlocks.id(name), operator.apply(ComponentType.builder()).build());
    }

    public static void init()
    {
    }
}
