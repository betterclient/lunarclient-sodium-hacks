package io.github.betterclient.lunarutil;

import io.github.betterclient.lunarutil.mod.impl.*;
import io.github.betterclient.lunarutil.ui.ClickGui;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

@SuppressWarnings("unused")
public class InjectedClientMod implements ClientModInitializer {

    public KeyMapping map1;

    public static InjectedClientMod instance;

    @Override
    public void onInitializeClient() {
        instance = this;
        map1 = KeyBindingHelper.registerKeyBinding(new KeyMapping("Open ClickGui", GLFW.GLFW_KEY_F9, "key.categories.gameplay") {
            @Override
            public void setDown(boolean bl) {
                if(bl)
                    Minecraft.getInstance().setScreen(new ClickGui());

                super.setDown(bl);
            }
        });

        ModMan.modules.addAll(Arrays.asList(
                new NoHurtCam(),
                new TriggerBot(),
                new CrystalOptimizer(),
                new VanillaFly(),
                new Speed(),
                new FastPlace(),
                new ModArrayList(),
                new FPS(),
                new HitBoxes()
        ));
    }
}
