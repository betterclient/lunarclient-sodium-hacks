package io.github.betterclient.lunarutil.mod.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.betterclient.lunarutil.mod.Module;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;

public class VanillaFly extends Module implements HudRenderCallback {
    public VanillaFly() {
        super(0, "VanillaFly");
        HudRenderCallback.EVENT.register(this);
    }

    @Override
    public void onDisable() {
        assert Minecraft.getInstance().player != null;
        if(!Minecraft.getInstance().player.isCreative())
            Minecraft.getInstance().player.getAbilities().flying = false;
    }

    @Override
    public void onHudRender(PoseStack matrixStack, float tickDelta) {
        if(this.enabled) {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.getAbilities().flying = true;
        }
    }
}
