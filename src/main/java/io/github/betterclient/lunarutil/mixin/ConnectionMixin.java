package io.github.betterclient.lunarutil.mixin;

import io.github.betterclient.lunarutil.ModMan;
import io.github.betterclient.lunarutil.mod.impl.CrystalOptimizer;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class ConnectionMixin {
    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"))
    private void onPacketSend(Packet<?> packet, CallbackInfo ci) {
        if(ModMan.getModule("CrystalOptimizer").enabled) {
            if (packet instanceof ServerboundInteractPacket interactPacket) {
                interactPacket.dispatch(new CrystalOptimizer.Handler());
            }
        }
    }
}
