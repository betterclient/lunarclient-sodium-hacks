package io.github.betterclient.lunarutil;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.betterclient.lunarutil.mod.Module;
import io.github.betterclient.lunarutil.mod.impl.*;
import io.github.betterclient.lunarutil.ui.ClickGui;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

@SuppressWarnings("unused")
public class InjectedClientMod implements ClientModInitializer, HudRenderCallback {

    public KeyMapping map1;

    public static InjectedClientMod instance;

    @Override
    public void onInitializeClient() {
        instance = this;
        KeyBindingHelper.registerKeyBinding(map1 = new KeyMapping("Open ClickGui", GLFW.GLFW_KEY_F9, "key.categories.gameplay"));

        ModMan.modules.addAll(Arrays.asList(
                new NoHurtCam(),
                new TriggerBot(),
                new CrystalOptimizer(),
                new VanillaFly(),
                new XRay(),
                new Speed()
        ));

        HudRenderCallback.EVENT.register(this);
    }

    @Override
    public void onHudRender(PoseStack matrixStack, float tickDelta) {
        for(Module mod : ModMan.getModules()) {
            if(InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), mod.key)) {
                mod.toggle();
            }
        }

        if(map1.isDown()) {
            Minecraft.getInstance().setScreen(new ClickGui());
        }
    }
}
