package io.github.betterclient.lunarutil.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.betterclient.lunarutil.ModMan;
import io.github.betterclient.lunarutil.mod.Module;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyMapping.class)
public class KeyMappingMixin {

    @Inject(method = "click", at = @At("RETURN"))
    private static void onClick(InputConstants.Key key, CallbackInfo ci) {
        ModMan.getModules().stream().filter(it -> it.key != 0).filter(it -> it.key == key.getValue()).forEach(Module::toggle);
    }
}