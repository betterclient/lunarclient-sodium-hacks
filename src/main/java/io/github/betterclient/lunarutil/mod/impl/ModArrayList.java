package io.github.betterclient.lunarutil.mod.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.betterclient.lunarutil.ModMan;
import io.github.betterclient.lunarutil.mod.Module;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

import java.util.concurrent.atomic.AtomicInteger;

public class ModArrayList extends Module implements HudRenderCallback {
    public Minecraft client;

    public ModArrayList() {
        super(0, "ArrayList");
        HudRenderCallback.EVENT.register(this);
    }

    @Override
    public void onHudRender(PoseStack matrixStack, float tickDelta) {
        this.client = Minecraft.getInstance();
        Font font = client.font;

        if(this.enabled) {
            int x = this.client.getWindow().getGuiScaledWidth() - 5;
            AtomicInteger y = new AtomicInteger(10);

            ModMan.getModules().stream().filter(it -> !it.name.equalsIgnoreCase("arraylist")).filter(Module::isEnabled).sorted((it, it1) -> font.width(it.name) > font.width(it1.name) ? -1 : 0).forEach(module -> font.draw(matrixStack, module.name, x - font.width(module.name), y.getAndAdd(10), -1));
        }
    }
}
