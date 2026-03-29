package com.mosadie.simplemainmenu.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mosadie.simplemainmenu.duck.MultilineSplashTextRenderer;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SplashRenderer.class)
public abstract class SplashTextRendererMixin implements MultilineSplashTextRenderer {
    @Unique
    private Component[] smm_lib$multilineText;

    @WrapOperation(
            method = "extractRenderState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Font;width(Lnet/minecraft/network/chat/FormattedText;)I"
            )
    )
    private int smm_lib$getMultilineTextWidth(Font instance, FormattedText text, Operation<Integer> original) {
        if (smm_lib$multilineText == null) return original.call(instance, text);

        int result = 0;
        for (Component line : smm_lib$multilineText) {
            result = Math.max(original.call(instance, line), result);
        }
        return result;
    }

    @WrapOperation(
            method = "extractRenderState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/ActiveTextCollector;accept(Lnet/minecraft/client/gui/TextAlignment;IILnet/minecraft/client/gui/ActiveTextCollector$Parameters;Lnet/minecraft/network/chat/Component;)V"
            )
    )
    private void smm_lib$renderMultilineText(ActiveTextCollector instance, TextAlignment alignment, int x, int y, ActiveTextCollector.Parameters parameters, Component text, Operation<Void> original) {
        if (smm_lib$multilineText == null) {
            original.call(instance, alignment, x, y, parameters, text);
            return;
        }

        for (Component line : smm_lib$multilineText) {
            // Use center alignment as then... idk I'd say it looks better
            original.call(instance, TextAlignment.CENTER, 0, y, parameters, line);
            y += 9;
        }
    }

    @Override
    public void smm_lib$setMultilineText(Component[] multilineText) {
        this.smm_lib$multilineText = multilineText;
    }
}
