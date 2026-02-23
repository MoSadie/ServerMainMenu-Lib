package com.mosadie.simplemainmenu.mixin;

import com.mosadie.simplemainmenu.client.SimpleMainMenuLibClient;
import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.MinecraftClient;
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
import org.spongepowered.asm.mixin.Mixin;
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
            this.splashText = new SplashTextRenderer(SimpleMainMenuLibClient.getSplashText());
    }

    @Redirect(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;addNormalWidgets(II)I"))
    private int redirectInitWidgetsNormal(TitleScreen self, int y, int spacingY) {
        int buttonYMulti = 0;

        int buttonCount = 0;

        if (SimpleMainMenuLibClient.isSingleplayerVisible()) buttonCount++;
        if (SimpleMainMenuLibClient.isMultiplayerVisible()) buttonCount++;
        if (SimpleMainMenuLibClient.isQuickJoinVisible()) buttonCount++;

        if (buttonCount == 1) {
            buttonYMulti = 1;
        }

        if (SimpleMainMenuLibClient.isSingleplayerVisible()) {
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

        if (SimpleMainMenuLibClient.isQuickJoinVisible()) {

            ButtonWidget.Builder quickJoinButtonWidgetBuilder = ButtonWidget.builder(SimpleMainMenuLibClient.getButtonText(), (button) -> {
                SimpleMainMenuLibClient.onQuickJoinClick();
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

        if (SimpleMainMenuLibClient.isMultiplayerVisible()) {
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

        if (SimpleMainMenuLibClient.isModsVisible()) {
            ButtonWidget.Builder modsButtonWidgetBuilder = ButtonWidget.builder(ModMenu.createModsButtonText(true), button -> {
                Screen modsScreen = ModMenuApi.createModsScreen(MinecraftClient.getInstance().currentScreen);
                MinecraftClient.getInstance().setScreen(modsScreen);
            }).position(self.width / 2 + 104, y + spacingY).size(50, 20);

            ButtonWidget modsButtonWidget = modsButtonWidgetBuilder.build();

            this.addDrawableChild(modsButtonWidget);
        }

        return y + (--buttonYMulti * spacingY);
    }
}
