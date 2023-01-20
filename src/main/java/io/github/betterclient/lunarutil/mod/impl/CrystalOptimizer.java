package io.github.betterclient.lunarutil.mod.impl;

import io.github.betterclient.lunarutil.mixin.ConnectionMixin;
import io.github.betterclient.lunarutil.mod.Module;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class CrystalOptimizer extends Module {

    public CrystalOptimizer() {
        super(0, "CrystalOptimizer");
    }

    public static class Handler implements ServerboundInteractPacket.Handler {
        public void onInteraction(InteractionHand hand) {}

        public void onInteraction(InteractionHand hand, Vec3 pos) {}

        public void onAttack() {
            Minecraft mc = Minecraft.getInstance();
            HitResult hitResult = mc.hitResult;
            if (hitResult == null)
                return;
            if (hitResult.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHitResult = (EntityHitResult)hitResult;
                Entity entity = entityHitResult.getEntity();
                if (entity instanceof EndCrystal) {

                    assert mc.player != null;
                    MobEffectInstance weakness = mc.player.getEffect(MobEffects.WEAKNESS);
                    MobEffectInstance strength = mc.player.getEffect(MobEffects.DAMAGE_BOOST);

                    if (weakness != null && (strength == null || strength.getAmplifier() <= weakness.getAmplifier()))
                        if (!this.isTool(mc.player.getMainHandItem()))
                            return;
                    entity.kill();
                    entity.setRemoved(Entity.RemovalReason.KILLED);
                    entity.onClientRemoval();
                }
            }
        }

        private boolean isTool(ItemStack itemStack) {
            if (!(itemStack.getItem() instanceof TieredItem) || itemStack.getItem() instanceof HoeItem)
                return false;
            Tier material = ((TieredItem)itemStack.getItem()).getTier();
            return (material == Tiers.DIAMOND || material == Tiers.NETHERITE);
        }
    }
}
