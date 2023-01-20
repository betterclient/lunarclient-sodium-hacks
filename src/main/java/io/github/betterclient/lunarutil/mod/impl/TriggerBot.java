package io.github.betterclient.lunarutil.mod.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.betterclient.lunarutil.mod.Module;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Timer;
import java.util.TimerTask;

public class TriggerBot extends Module implements HudRenderCallback{

    public Entity toHit;

    public TriggerBot() {
        super(0, "TriggerBot");
        HudRenderCallback.EVENT.register(this);
    }

    @Override
    public void onHudRender(PoseStack matrixStack, float tickDelta) {
        if(!this.enabled)
            return;
        Minecraft client = Minecraft.getInstance();

        if(client.hitResult != null && client.player != null && client.player.getAttackStrengthScale(client.getDeltaFrameTime()) >= 1 && client.hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult hr = (EntityHitResult) client.hitResult;
            if(!(hr.getEntity() instanceof ItemEntity)) {
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
