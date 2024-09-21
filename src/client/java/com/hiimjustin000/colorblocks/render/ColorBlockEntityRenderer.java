package com.hiimjustin000.colorblocks.render;

import com.hiimjustin000.colorblocks.ColorBlocks;
import com.hiimjustin000.colorblocks.block.entity.ColorBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;

public class ColorBlockEntityRenderer implements BlockEntityRenderer<ColorBlockEntity>
{
    public static final SpriteIdentifier TEXTURE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, ColorBlocks.id("block/color_block"));
    public static final EntityModelLayer LAYER = new EntityModelLayer(ColorBlocks.id("block/color_block"), "main");

    private final ModelPart model;

    public ColorBlockEntityRenderer(BlockEntityRendererFactory.Context context)
    {
        model = MinecraftClient.getInstance().getEntityModelLoader().getModelPart(LAYER);
    }

    @Override
    public boolean rendersOutsideBoundingBox(ColorBlockEntity blockEntity)
    {
        return true;
    }

    @Override
    public int getRenderDistance()
    {
        return 256;
    }

    public static TexturedModelData getTexturedModelData()
    {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(-8f, -8f, -8f, 16f, 16f, 16f), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void render(ColorBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
    {
        matrices.push();
        matrices.translate(.5f, .5f, .5f);
        model.render(matrices, TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid),
                light, overlay, entity.getDisplayColor() | 0xff000000);
        matrices.pop();
    }
}
