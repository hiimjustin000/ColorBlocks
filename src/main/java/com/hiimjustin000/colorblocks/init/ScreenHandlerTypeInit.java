package com.hiimjustin000.colorblocks.init;

import com.hiimjustin000.colorblocks.ColorBlocks;
import com.hiimjustin000.colorblocks.handler.ColorBlockScreenHandler;
import com.hiimjustin000.colorblocks.network.BlockPosPayload;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;

public class ScreenHandlerTypeInit
{
    public static final ExtendedScreenHandlerType<ColorBlockScreenHandler, BlockPosPayload> COLOR_BLOCK =
            register("color_block", ColorBlockScreenHandler::new, BlockPosPayload.CODEC);

    public static <T extends ScreenHandler, D extends CustomPayload> ExtendedScreenHandlerType<T, D> register(
            String id, ExtendedScreenHandlerType.ExtendedFactory<T, D> factory, PacketCodec<? super RegistryByteBuf, D> codec)
    {
        return Registry.register(Registries.SCREEN_HANDLER, ColorBlocks.id(id), new ExtendedScreenHandlerType<>(factory, codec));
    }

    public static void init()
    {
    }
}
