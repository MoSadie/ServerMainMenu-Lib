package com.mosadie.servermainmenu.mixin;

import com.mosadie.servermainmenu.client.ServerMainMenuLibClient;
import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerWarningScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsNotificationsScreen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    @Shadow @Nullable private SplashTextRenderer splashText;
    @Shadow @Nullable private RealmsNotificationsScreen realmsNotificationGui;

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "Lnet/minecraft/client/gui/screen/TitleScreen;isRealmsNotificationsGuiDisplayed()Z", at = @At("HEAD"), cancellable = true)
    private void injectRealmNotification(CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(false);
    }

    @Inject(method = "onDisplayed", at = @At("HEAD"), cancellable = true)
    private void injectOnDisplayed(CallbackInfo ci) {
        super.onDisplayed();
        ci.cancel();
    }

    @Inject(method = "init()V", at = @At("HEAD"))
    private void injectSplashText(CallbackInfo info) {
        if (splashText == null)
            this.splashText = new SplashTextRenderer(ServerMainMenuLibClient.getSplashText());
    }

    @Redirect(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;initWidgetsNormal(II)V"))
    private void redirectInitWidgetsNormal(TitleScreen self, int y, int spacingY) {
        int buttonYMulti = 0;

        int buttonCount = 0;

        if (ServerMainMenuLibClient.isSingleplayerVisible()) buttonCount++;
        if (ServerMainMenuLibClient.isMultiplayerVisible()) buttonCount++;
        if (ServerMainMenuLibClient.isQuickJoinVisible()) buttonCount++;

        if (buttonCount == 1) {
            buttonYMulti = 1;
        }

        if (ServerMainMenuLibClient.isSingleplayerVisible()) {
            ButtonWidget.Builder singlePlayerButtonWidgetBuilder = ButtonWidget.builder(Text.translatable("menu.singleplayer"), (button -> {
                        MinecraftClient.getInstance().setScreen(new SelectWorldScreen((self)));
                    }))
                    .size(200, 20)
                    .position(self.width / 2 - 100, y);

            this.addDrawableChild(singlePlayerButtonWidgetBuilder.build());

            buttonYMulti++;
        }

        final Text disabledText = ((TitleScreenInvoker) this).invokeGetMultiplayerDisabledText();
        boolean isDisabled = disabledText != null;

        Tooltip tooltip = Tooltip.of(disabledText);

        if (ServerMainMenuLibClient.isQuickJoinVisible()) {

            ButtonWidget.Builder quickJoinButtonWidgetBuilder = ButtonWidget.builder(ServerMainMenuLibClient.getButtonText(), (button) -> {
                ServerMainMenuLibClient.onQuickJoinClick();
            }).position(self.width / 2 - 100, y + (spacingY * buttonYMulti++)).size(200, 20);

            // Technically as of v2.0.0, this button can connect to things outside of multiplayer,
            // but since it's possible, still going to disable it.

            if (isDisabled) {
                quickJoinButtonWidgetBuilder.tooltip(tooltip);
            }

            ButtonWidget joinServerButtonWidget = quickJoinButtonWidgetBuilder.build();
            joinServerButtonWidget.active = !isDisabled;
            this.addDrawableChild(quickJoinButtonWidgetBuilder.build());
        }

        if (ServerMainMenuLibClient.isMultiplayerVisible()) {
            ButtonWidget.Builder multiplayerButtonWidgetBuilder = ButtonWidget.builder(Text.translatable("menu.multiplayer"), button -> {
                Screen screen = MinecraftClient.getInstance().options.skipMultiplayerWarning ? new MultiplayerScreen(self) : new MultiplayerWarningScreen(self);
                MinecraftClient.getInstance().setScreen(screen);
            }).position(self.width / 2 - 100, y + (spacingY * buttonYMulti++)).size(200, 20);

            if (isDisabled)
                multiplayerButtonWidgetBuilder.tooltip(tooltip);

            ButtonWidget multiplayerButtonWidget = multiplayerButtonWidgetBuilder.build();
            multiplayerButtonWidget.active = !isDisabled;

            this.addDrawableChild(multiplayerButtonWidget);
        }

        if (ServerMainMenuLibClient.isModsVisible()) {
            ButtonWidget.Builder modsButtonWidgetBuilder = ButtonWidget.builder(ModMenu.createModsButtonText(true), button -> {
                Screen modsScreen = ModMenuApi.createModsScreen(MinecraftClient.getInstance().currentScreen);
                MinecraftClient.getInstance().setScreen(modsScreen);
            }).position(self.width / 2 + 104, y + spacingY).size(50, 20);

            ButtonWidget modsButtonWidget = modsButtonWidgetBuilder.build();

            this.addDrawableChild(modsButtonWidget);
        }

    }
}
