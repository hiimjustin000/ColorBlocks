package com.hiimjustin000.colorblocks.network;

import com.hiimjustin000.colorblocks.ColorBlocks;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record UpdateColorPayload(ColorData data) implements CustomPayload
{
    public static final Id<UpdateColorPayload> ID = new Id<>(ColorBlocks.id("update_color"));
    public static final PacketCodec<RegistryByteBuf, UpdateColorPayload> CODEC = PacketCodec.tuple(
            ColorData.PACKET_CODEC, UpdateColorPayload::data, UpdateColorPayload::new);

    @Override
    public Id<? extends CustomPayload> getId()
    {
        return ID;
    }
}
