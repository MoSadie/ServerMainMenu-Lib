package com.mosadie.servermainmenu.mixin;

import com.mosadie.servermainmenu.client.ServerMainMenuLibClient;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Screen.class)
public class ScreenMixin {
    @Shadow
    protected static final CubeMapRenderer PANORAMA_RENDERER = new CubeMapRenderer(ServerMainMenuLibClient.getTheme().getPanorama());
//    private static RotatingCubeMapRenderer SSM_PANO_RENDERER = new RotatingCubeMapRenderer(new CubeMapRenderer(ServerMainMenuLibClient.getTheme().getPanorama()));
//
//    @Shadow
//    private int width;
//    @Shadow
//    private int height;

//    /**
//     * @author MoSadie
//     * @reason Replaces a one-line method call to render the panorama background with a different renderer.
//     */
//    @Overwrite
//    protected void renderPanoramaBackground(DrawContext context, float deltaTicks) {
//        SSM_PANO_RENDERER.render(context, this.width, this.height, 1.0f, deltaTicks);
//    }
//    @Redirect(method = "renderBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;renderPanoramaBackground(Lnet/minecraft/client/gui/DrawContext;F)V"))
//    private void redirectRenderPanoramaBackground(Screen self, DrawContext context, float deltaTicks) {
//        ROTATING_PANORAMA_RENDERER.render(context, self.width, self.height, 1.0f, deltaTicks);
//    }
}
