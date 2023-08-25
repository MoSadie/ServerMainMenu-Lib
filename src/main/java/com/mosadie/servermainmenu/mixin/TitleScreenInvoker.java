package com.mosadie.servermainmenu.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TitleScreen.class)
public interface TitleScreenInvoker {

    @Invoker("getMultiplayerDisabledText")
    Text invokeGetMultiplayerDisabledText();
}
