package com.hiimjustin000.colorblocks.render;

import com.hiimjustin000.colorblocks.block.entity.ColorBlockEntity;
import com.hiimjustin000.colorblocks.init.BlockInit;
import com.hiimjustin000.colorblocks.item.ColorBlockItem;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class ColorBlockItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer
{
    public final Map<ItemStack, ColorBlockEntity> entities = new HashMap<>();

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
    {
        if (stack.getItem() instanceof ColorBlockItem)
        {
            if (!entities.containsKey(stack))
            {
                ColorBlockEntity entity = new ColorBlockEntity(BlockPos.ORIGIN, BlockInit.COLOR_BLOCK.getDefaultState());
                entity.readComponents(stack);
                entities.put(stack, entity);
            }
            ColorBlockEntity entity = entities.get(stack);
            if (!entity.getColors().isEmpty())
                entity.checkTick(MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(false));
            MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(entity, matrices, vertexConsumers, light, overlay);
        }
    }
}
