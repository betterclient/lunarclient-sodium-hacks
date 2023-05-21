package io.github.betterclient.lunarutil.mixin;

import io.github.betterclient.lunarutil.ModMan;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "getPickRadius", at = @At("HEAD"), cancellable = true)
    public void hitboxes(CallbackInfoReturnable<Float> cir) {
        if(ModMan.getModule("Hitboxes").enabled && ((Entity) (Object) this) instanceof Player) {
            cir.setReturnValue(0.3F);
        }
    }
}
