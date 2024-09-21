package com.hiimjustin000.colorblocks.block;

import com.hiimjustin000.colorblocks.block.entity.ColorBlockEntity;
import com.hiimjustin000.colorblocks.init.BlockEntityTypeInit;
import com.hiimjustin000.colorblocks.init.ComponentTypeInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class ColorBlock extends Block implements BlockEntityProvider
{
    public ColorBlock()
    {
        super(AbstractBlock.Settings.create().requiresTool().strength(1.8f));
    }

    @Override
    protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos)
    {
        return state.getFluidState().isEmpty();
    }

    @Override
    protected int getOpacity(BlockState state, BlockView world, BlockPos pos)
    {
        return state.isTransparent(world, pos) ? 0 : 1;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit)
    {
        if (!world.isClient())
            world.getBlockEntity(pos, BlockEntityTypeInit.COLOR_BLOCK_ENTITY).ifPresent(player::openHandledScreen);
        return ActionResult.success(world.isClient());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
    {
        return BlockEntityTypeInit.COLOR_BLOCK_ENTITY.instantiate(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type)
    {
        if (world.isClient())
            return (world1, pos, state1, blockEntity) -> ((ColorBlockEntity) blockEntity).tick();
        return null;
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state)
    {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof ColorBlockEntity colorBlockEntity)
        {
            ItemStack stack = new ItemStack(this);
            stack.set(ComponentTypeInit.COLOR, colorBlockEntity.getColor());
            stack.set(ComponentTypeInit.COLORS, colorBlockEntity.getColors());
            stack.set(ComponentTypeInit.SPEED, colorBlockEntity.getSpeed());
            return stack;
        }
        return super.getPickStack(world, pos, state);
    }
}
