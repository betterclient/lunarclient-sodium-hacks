package io.github.betterclient.lunarutil.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.betterclient.lunarutil.ModMan;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "bobHurt", at = @At("HEAD"), cancellable = true)
    public void disableHurtCam(PoseStack matrices, float tickDelta, CallbackInfo ci) {
        if (ModMan.getModule("NoHurtCam").enabled) ci.cancel();
    }
}
