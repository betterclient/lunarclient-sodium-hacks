package io.github.betterclient.lunarutil.ui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.betterclient.lunarutil.ModMan;
import io.github.betterclient.lunarutil.mod.Module;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class ClickGui extends Screen {
    public boolean shouldLookForKey = false;
    public Module lookingKey;

    public int modEnabledColor, modDisabledColor, blackColor;

    public ClickGui() {
        super(Component.empty());
    }

    @Override
    protected void init() {
        modEnabledColor = new Color(0, 0, 255).getRGB();
        modDisabledColor = new Color(255, 255, 255).getRGB();
        blackColor = new Color(0, 0, 0).getRGB();

        super.init();
    }

    @Override
    @SuppressWarnings("all")
    public void render(PoseStack poseStack, int i, int j, float f) {
        super.renderBackground(poseStack);

        this.font.draw(poseStack, "Lunar Util Client ClickGui", width / 2 - 100, 10, -1);

        if(this.shouldLookForKey) {
            poseStack.pushPose();
            poseStack.scale(1.5f, 1.5f, 1f);
            this.minecraft.font.draw(poseStack, "Please click a button on your keyboard to set keybind", 10, height / 2 - 4, -1);
            poseStack.popPose();
        } else {
            this.minecraft.font.draw(poseStack, "Left Click To Toggle", 0, height - 20, -1);
            this.minecraft.font.draw(poseStack, "Middle Click To Select KeyBind", 0, height - 10, -1);

            Gui.fill(poseStack, width / 2 - 150, height / 2 - 150, width / 2 + 150, height / 2 + 150, -1);

            int y = height / 2 - 150;
            for(Module mod : ModMan.getModules()) {
                y+=13;
                Gui.fill(poseStack, width / 2 - 150, y, width / 2 + 150, y + 10, mod.enabled ? modEnabledColor : modDisabledColor);
                this.minecraft.font.draw(poseStack, mod.name, width / 2 - 40, y + 1, blackColor);
            }
        }

        super.render(poseStack, i, j, f);
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        if(this.shouldLookForKey) {
            this.shouldLookForKey = false;
            this.lookingKey.key = i;
        }

        return super.keyPressed(i, j, k);
    }

    @Override
    @SuppressWarnings("all")
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        int y = height / 2 - 150;
        for(Module mod : ModMan.getModules()) {
            y+=13;

            if(mouseY >= y && mouseY <= (y + 10)) {
                if(mouseX >= ((int)(width / 2 - 150)) && mouseX <= (width / 2 + 150)) {
                    if(button == 0)
                        mod.toggle();
                    if(button == 2) {
                        this.shouldLookForKey = true;
                        this.lookingKey = mod;
                    }
                }
            }
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }
}
