package com.mosadie.servermainmenu.mixin;

import com.mosadie.servermainmenu.client.ServerMainMenuLibClient;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Screen.class)
public class ScreenMixin {
    @Final
    @Mutable
    @Shadow @Nullable public static RotatingCubeMapRenderer ROTATING_PANORAMA_RENDERER = new RotatingCubeMapRenderer(new CubeMapRenderer(ServerMainMenuLibClient.getTheme().getPanorama()));
}
