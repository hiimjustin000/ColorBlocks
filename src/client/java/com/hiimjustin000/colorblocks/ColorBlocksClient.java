package com.hiimjustin000.colorblocks;

import com.hiimjustin000.colorblocks.block.entity.ColorBlockEntity;
import com.hiimjustin000.colorblocks.init.BlockEntityTypeInit;
import com.hiimjustin000.colorblocks.init.BlockInit;
import com.hiimjustin000.colorblocks.init.ItemInit;
import com.hiimjustin000.colorblocks.init.ScreenHandlerTypeInit;
import com.hiimjustin000.colorblocks.render.ColorBlockEntityRenderer;
import com.hiimjustin000.colorblocks.render.ColorBlockItemRenderer;
import com.hiimjustin000.colorblocks.screen.ColorBlockScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class ColorBlocksClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        EntityModelLayerRegistry.registerModelLayer(ColorBlockEntityRenderer.LAYER, ColorBlockEntityRenderer::getTexturedModelData);
        BlockEntityRendererFactories.register(BlockEntityTypeInit.COLOR_BLOCK_ENTITY, ColorBlockEntityRenderer::new);
        BuiltinItemRendererRegistry.INSTANCE.register(ItemInit.COLOR_BLOCK, new ColorBlockItemRenderer());
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world == null || pos == null)
                return -1;
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity == null)
                return -1;
            return entity instanceof ColorBlockEntity ? ((ColorBlockEntity)entity).getDisplayColor() | 0xff000000 : -1;
        }, BlockInit.COLOR_BLOCK);
        HandledScreens.register(ScreenHandlerTypeInit.COLOR_BLOCK, ColorBlockScreen::new);
    }
}
