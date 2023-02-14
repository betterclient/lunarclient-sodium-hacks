package io.github.betterclient.lunarutil.mod.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.betterclient.lunarutil.mod.Module;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ArmorStandItem;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Timer;

public class TriggerBot extends Module implements HudRenderCallback{

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
                new Timer().schedule(new TimerTask(hr.getEntity()), (long) (Math.random() * 30));
            }
        }
    }

    static class TimerTask extends java.util.TimerTask {
        final Entity toHit;

        TimerTask(Entity toHit) {
            this.toHit = toHit;
        }

        @Override
        public void run() {
            Minecraft client = Minecraft.getInstance();
            assert client.hitResult != null;

            if(client.hitResult.getType() == HitResult.Type.ENTITY && ((EntityHitResult) client.hitResult).getEntity().equals(toHit)) {
                assert client.gameMode != null;
                assert client.player != null;
                client.gameMode.attack(client.player, toHit);
                client.player.swing(InteractionHand.MAIN_HAND);
            }
        }
    }
}
