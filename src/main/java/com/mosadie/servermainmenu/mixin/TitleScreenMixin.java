package com.mosadie.servermainmenu.mixin;

import com.mosadie.servermainmenu.client.ServerMainMenuLibClient;
import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerWarningScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
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

    @Final @Mutable
    @Shadow @Nullable public static CubeMapRenderer PANORAMA_CUBE_MAP = new CubeMapRenderer(ServerMainMenuLibClient.getTheme().getPanorama());

    @Shadow @Nullable private RealmsNotificationsScreen realmsNotificationGui;

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "isRealmsNotificationsGuiDisplayed", at = @At("HEAD"), cancellable = true)
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
        this.splashText = new SplashTextRenderer(ServerMainMenuLibClient.getSplashText());
    }

    @Redirect(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;initWidgetsNormal(II)V"))
    private void redirectInitWidgetsNormal(TitleScreen self, int y, int spacingY) {
        ButtonWidget.Builder singlePlayerButtonWidgetBuilder = ButtonWidget.builder(Text.translatable("menu.singleplayer"), (button -> {
                    MinecraftClient.getInstance().setScreen(new SelectWorldScreen((self)));
                }))
                .size(200, 20)
                .position(self.width / 2 - 100, y);

        this.addDrawableChild(singlePlayerButtonWidgetBuilder.build());

        final Text disabledText = ((TitleScreenInvoker) this).invokeGetMultiplayerDisabledText();
        boolean isDisabled = disabledText != null;

        Tooltip tooltip = Tooltip.of(disabledText);

        ButtonWidget.Builder joinServerButtonWidgetBuilder = ButtonWidget.builder(ServerMainMenuLibClient.getButtonText(), (button) -> {
            ServerInfo serverInfo = ServerMainMenuLibClient.getButtonServerInfo();
            serverInfo.setResourcePackPolicy(ServerInfo.ResourcePackPolicy.ENABLED);
            ConnectScreen.connect(self, MinecraftClient.getInstance(), ServerAddress.parse(serverInfo.address), serverInfo, false);
        }).position(self.width / 2 - 100, y + spacingY).size(200, 20);

        if (isDisabled) {
            joinServerButtonWidgetBuilder.tooltip(tooltip);
        }

        ButtonWidget joinServerButtonWidget = joinServerButtonWidgetBuilder.build();
        joinServerButtonWidget.active = !isDisabled;
        this.addDrawableChild(joinServerButtonWidgetBuilder.build());

        ButtonWidget.Builder multiplayerButtonWidgetBuilder = ButtonWidget.builder(Text.translatable("menu.multiplayer"), button -> {
            Screen screen = MinecraftClient.getInstance().options.skipMultiplayerWarning ? new MultiplayerScreen(self) : new MultiplayerWarningScreen(self);
            MinecraftClient.getInstance().setScreen(screen);
        }).position(self.width / 2 - 100, y + spacingY * 2).size(200, 20);

        if (isDisabled)
            multiplayerButtonWidgetBuilder.tooltip(tooltip);

        ButtonWidget multiplayerButtonWidget = multiplayerButtonWidgetBuilder.build();
        multiplayerButtonWidget.active = !isDisabled;

        this.addDrawableChild(multiplayerButtonWidget);

        ButtonWidget.Builder modsButtonWidgetBuilder = ButtonWidget.builder(ModMenu.createModsButtonText(true), button -> {
            Screen modsScreen = ModMenuApi.createModsScreen(MinecraftClient.getInstance().currentScreen);
            MinecraftClient.getInstance().setScreen(modsScreen);
        }).position(self.width / 2 + 104, y + spacingY).size(50, 20);

        ButtonWidget modsButtonWidget = modsButtonWidgetBuilder.build();

        this.addDrawableChild(modsButtonWidget);

    }
}
