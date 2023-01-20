package io.github.betterclient.lunarutil.mixin;

import io.github.betterclient.lunarutil.ModMan;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlock {
    @Inject(at = @At("RETURN"), method = "shouldRenderFace", cancellable = true)
    private static void shouldDrawSide(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction, BlockPos blockPos2, CallbackInfoReturnable<Boolean> cir) {
        assert Minecraft.getInstance().level != null;
        Block block = Minecraft.getInstance().level.getBlockState(blockPos).getBlock();
        if(ModMan.getModule("XRay").enabled &&  block instanceof DropExperienceBlock) {
            cir.setReturnValue(true);
        }
    }
}
