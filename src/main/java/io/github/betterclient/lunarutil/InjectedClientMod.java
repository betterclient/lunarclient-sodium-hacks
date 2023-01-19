package io.github.betterclient.lunarutil;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.lwjgl.glfw.GLFW;

import java.util.Timer;
import java.util.TimerTask;

public class InjectedClientMod implements ClientModInitializer, HudRenderCallback {

    public KeyMapping map;
    public boolean isToggled = false;
    public Entity toHit;

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(map = new KeyMapping("Open Cheats", GLFW.GLFW_KEY_V, "key.categories.gameplay"));

        HudRenderCallback.EVENT.register(this);
    }

    @Override
    public void onHudRender(PoseStack matrixStack, float tickDelta) { //Basic trigger bot
        if(map.isDown()) {
            isToggled = !isToggled;
            System.gc();
        }
        Minecraft client = Minecraft.getInstance();

        if(client.hitResult != null && client.player != null && client.player.getAttackStrengthScale(client.getDeltaFrameTime()) >= 1 && client.hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult hr = (EntityHitResult) client.hitResult;
            if(!(hr.getEntity() instanceof ItemEntity) && isToggled) {
                toHit = hr.getEntity();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        assert client.gameMode != null;
                        client.gameMode.attack(client.player, toHit);
                        client.player.swing(InteractionHand.MAIN_HAND);
                    }
                }, (long) (Math.random() * 30));
            }
        }
    }
}
