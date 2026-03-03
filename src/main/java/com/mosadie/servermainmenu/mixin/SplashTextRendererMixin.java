package com.mosadie.servermainmenu.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mosadie.servermainmenu.duck.MultilineSplashTextRenderer;
import net.minecraft.client.font.Alignment;
import net.minecraft.client.font.DrawnTextConsumer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SplashTextRenderer.class)
public abstract class SplashTextRendererMixin implements MultilineSplashTextRenderer {
    @Unique
    private Text[] smm_lib$multilineText;

    @WrapOperation(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/font/TextRenderer;getWidth(Lnet/minecraft/text/StringVisitable;)I"
            )
    )
    private int smm_lib$getMultilineTextWidth(TextRenderer instance, StringVisitable text, Operation<Integer> original) {
        if (smm_lib$multilineText == null) return original.call(instance, text);

        int result = 0;
        for (Text line : smm_lib$multilineText) {
            result = Math.max(original.call(instance, line), result);
        }
        return result;
    }

    @WrapOperation(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/font/DrawnTextConsumer;text(Lnet/minecraft/client/font/Alignment;IILnet/minecraft/client/font/DrawnTextConsumer$Transformation;Lnet/minecraft/text/Text;)V"
            )
    )
    private void smm_lib$renderMultilineText(DrawnTextConsumer instance, Alignment alignment, int x, int y, DrawnTextConsumer.Transformation transformation, Text text, Operation<Void> original) {
        if (smm_lib$multilineText == null) {
            original.call(instance, alignment, x, y, transformation, text);
            return;
        }

        for (Text line : smm_lib$multilineText) {
            // Use center alignment as then... idk I'd say it looks better
            original.call(instance, Alignment.CENTER, 0, y, transformation, line);
            y += 9;
        }
    }

    @Override
    public void smm_lib$setMultilineText(Text[] multilineText) {
        this.smm_lib$multilineText = multilineText;
    }
}
