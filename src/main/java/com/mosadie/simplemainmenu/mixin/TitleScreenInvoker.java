package com.mosadie.simplemainmenu.mixin;

import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TitleScreen.class)
public interface TitleScreenInvoker {

    @Invoker("getMultiplayerDisabledReason")
    Component invokeGetMultiplayerDisabledReason();
}
