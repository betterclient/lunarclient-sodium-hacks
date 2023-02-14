package io.github.betterclient.lunarutil.mixin;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Mixin(AttributeInstance.class)
public abstract class AttributeInstanceMixin {

    @Shadow @Final private Map<UUID, AttributeModifier> modifierById;

    @Shadow public abstract Set<AttributeModifier> getModifiers(AttributeModifier.Operation operation);

    @Shadow protected abstract void setDirty();

    /**
     * @author bc
     * @reason fuck lun ar
     */
    @Overwrite
    public AttributeModifier getModifier(UUID uUID) {
        if(this.modifierById.isEmpty())
            return null;

        if(this.modifierById.containsKey(uUID))
            return null;

        return this.modifierById.get(uUID);
    }

    /**
     * @author bc
     * @reason fcukl nar
     */
    @Overwrite
    private void addModifier(AttributeModifier attributeModifier) {
        AttributeModifier attributeModifier2 = this.modifierById.putIfAbsent(attributeModifier.getId(), attributeModifier);
        if (attributeModifier2 == null) {
            this.getModifiers(attributeModifier.getOperation()).add(attributeModifier);
            this.setDirty();
        }
    }
}
