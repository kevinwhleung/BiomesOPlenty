/*******************************************************************************
 * Copyright 2021, the Glitchfiend Team.
 * All rights reserved.
 ******************************************************************************/
package biomesoplenty.common.block;

import biomesoplenty.api.block.BOPBlocks;
import biomesoplenty.api.block.BOPFluids;
import biomesoplenty.api.item.BOPItems;
import biomesoplenty.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class BloodFluid extends FlowingFluid
{
    @Override
    public Fluid getFlowing() {
        return BOPFluids.FLOWING_BLOOD;
    }

    @Override
    public Fluid getSource() {
        return BOPFluids.BLOOD;
    }

    @Override
    public Item getBucket() {
        return BOPItems.BLOOD_BUCKET;
    }

    @Override
    protected net.minecraftforge.fluids.FluidAttributes createAttributes()
    {
        return net.minecraftforge.fluids.FluidAttributes.builder(
            new ResourceLocation("biomesoplenty:block/blood_still"),
            new ResourceLocation("biomesoplenty:block/blood_flow"))
            .translationKey("block.biomesoplenty.blood").density(2000).viscosity(3500)
            .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
            .build(this);
    }

    @Nullable
    @Override
    public ParticleOptions getDripParticle() {
        return ParticleTypes.DRIPPING_LAVA;
    }

    @Override
    protected boolean canConvertToSource() {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor p_76450_, BlockPos p_76451_, BlockState p_76452_)
    {
        BlockEntity blockentity = p_76452_.hasBlockEntity() ? p_76450_.getBlockEntity(p_76451_) : null;
        Block.dropResources(p_76452_, p_76450_, p_76451_, blockentity);
    }

    @Override
    public int getSlopeFindDistance(LevelReader p_76464_) {
        return 3;
    }

    @Override
    public BlockState createLegacyBlock(FluidState p_76466_)
    {
        return BOPBlocks.BLOOD.defaultBlockState().setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(p_76466_)));
    }

    @Override
    public boolean isSame(Fluid p_76456_) {
        return p_76456_ == BOPFluids.BLOOD || p_76456_ == BOPFluids.FLOWING_BLOOD;
    }

    @Override
    public int getDropOff(LevelReader p_76469_) {
        return 1;
    }

    @Override
    public int getTickDelay(LevelReader p_76454_) {
        return 7;
    }

    @Override
    public boolean canBeReplacedWith(FluidState p_76458_, BlockGetter p_76459_, BlockPos p_76460_, Fluid p_76461_, Direction p_76462_)
    {
        return p_76462_ == Direction.DOWN && !p_76461_.is(ModTags.Fluids.BLOOD);
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL);
    }

    public static class Flowing extends BloodFluid
    {
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> p_76476_)
        {
            super.createFluidStateDefinition(p_76476_);
            p_76476_.add(LEVEL);
        }

        public int getAmount(FluidState p_76480_) {
            return p_76480_.getValue(LEVEL);
        }

        public boolean isSource(FluidState p_76478_) {
            return false;
        }
    }

    public static class Source extends BloodFluid
    {
        public int getAmount(FluidState p_76485_) {
            return 8;
        }

        public boolean isSource(FluidState p_76483_) {
            return true;
        }
    }
}