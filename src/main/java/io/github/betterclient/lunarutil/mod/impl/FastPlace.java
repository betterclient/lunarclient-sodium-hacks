package io.github.betterclient.lunarutil.mod.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.betterclient.lunarutil.mod.Module;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;

import java.lang.reflect.Field;

public class FastPlace extends Module implements HudRenderCallback {
    public FastPlace() {
        super(0, "FastPlace");
        HudRenderCallback.EVENT.register(this);
    }

    @Override
    public void onHudRender(PoseStack matrixStack, float tickDelta) {
        if(this.enabled) {
            try {
                Minecraft mc = Minecraft.getInstance();
                Field f = mc.getClass().getDeclaredField("rightClickDelay");
                f.setAccessible(true);
                f.set(mc, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
