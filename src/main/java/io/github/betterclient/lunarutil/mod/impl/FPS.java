package io.github.betterclient.lunarutil.mod.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.betterclient.lunarutil.mod.Module;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class FPS extends Module implements HudRenderCallback {
    public FPS() {
        super(0, "FPS");
        HudRenderCallback.EVENT.register(this);
    }

    List<Long> fps = new ArrayList<>();

    @Override
    public void onHudRender(PoseStack matrixStack, float tickDelta) {
        fps.add(System.currentTimeMillis());

        if(this.isEnabled()) {
            this.fps.removeIf(aLong -> aLong + 1000 < System.currentTimeMillis());
            int fps1 = this.fps.size();
            Minecraft.getInstance().font.draw(matrixStack, fps1 + " FPS", 20, 20, -1);
        }
    }
}
