package io.github.betterclient.lunarutil.mod.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.betterclient.lunarutil.mod.Module;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;

public class Speed extends Module implements HudRenderCallback {
    public Speed() {
        super(0, "Speed");
        HudRenderCallback.EVENT.register(this);
    }

    @Override
    public void onHudRender(PoseStack matrixStack, float tickDelta) {
        if(!this.enabled)
            return;

        Minecraft mc = Minecraft.getInstance();

        if(mc.player == null)
            return;

        if(mc.player.isOnGround())
            mc.player.jumpFromGround();
    }
}
