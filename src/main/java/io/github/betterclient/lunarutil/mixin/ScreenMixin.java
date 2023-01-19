package io.github.betterclient.lunarutil.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {
    @Shadow @Nullable protected Minecraft minecraft;

    @Inject(method = "render", at = @At("RETURN"))
    public void render(PoseStack poseStack, int i, int j, float f, CallbackInfo ci) {
        assert this.minecraft != null;
        this.minecraft.font.draw(poseStack, "Test Mixin!!!", i, j, -1);
    }
}
