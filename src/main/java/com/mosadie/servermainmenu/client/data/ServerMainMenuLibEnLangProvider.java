package com.mosadie.servermainmenu.client.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ServerMainMenuLibEnLangProvider extends FabricLanguageProvider {
    protected ServerMainMenuLibEnLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("text.smmlib.normaltheme.joinserver", "Join the server!");

        translationBuilder.add("text.autoconfig.smm-lib.title", "ServerMainMenu Lib Settings");

        translationBuilder.add("text.autoconfig.smm-lib.option.quickJoinButtonOptions", "Quick Join Button Settings");

        translationBuilder.add("text.autoconfig.smm-lib.option.quickJoinButtonOptions.overrideQuickJoinButton", "Override Quick Join Button?");
        translationBuilder.add("text.autoconfig.smm-lib.option.quickJoinButtonOptions.buttonTextOverride", "Button Text");
        translationBuilder.add("text.autoconfig.smm-lib.option.quickJoinButtonOptions.buttonNameOverride", "Button Server Name");
        translationBuilder.add("text.autoconfig.smm-lib.option.quickJoinButtonOptions.buttonNameOverride.@Tooltip", "Mainly used by mods such as ReplayMod to track what server you were on. Unused in World mode.");
        translationBuilder.add("text.autoconfig.smm-lib.option.quickJoinButtonOptions.buttonDestinationOverride", "Server Address / World Folder");
        translationBuilder.add("text.autoconfig.smm-lib.option.quickJoinButtonOptions.buttonType", "Destination Type");

        translationBuilder.add("text.autoconfig.smm-lib.option.visibilityOptions", "Visibility Options");

        translationBuilder.add("text.autoconfig.smm-lib.option.visibilityOptions.singleplayer", "Show Singleplayer Button");
        translationBuilder.add("text.autoconfig.smm-lib.option.visibilityOptions.multiplayer", "Show Multiplayer Button");
        translationBuilder.add("text.autoconfig.smm-lib.option.visibilityOptions.mods", "Show Mods Button");
        translationBuilder.add("text.autoconfig.smm-lib.option.visibilityOptions.quickJoin", "Show Quick Join Button");

        translationBuilder.add("text.autoconfig.smm-lib.option.themeOptions", "Theme Settings");

        translationBuilder.add("text.autoconfig.smm-lib.option.themeOptions.overrideTheme", "Override Menu Theme?");
        translationBuilder.add("text.autoconfig.smm-lib.option.themeOptions.overrideTheme.@Tooltip", "Theme will update on next game launch.");
        translationBuilder.add("text.autoconfig.smm-lib.option.themeOptions.themeNamespace", "Theme Namespace");
        translationBuilder.add("text.autoconfig.smm-lib.option.themeOptions.themeId", "Theme ID");

        translationBuilder.add("text.autoconfig.smm-lib.option.splashOptions", "Splash Message Settings");

        translationBuilder.add("text.autoconfig.smm-lib.option.splashOptions.overrideSplash", "Override Splash Text?");
        translationBuilder.add("text.autoconfig.smm-lib.option.splashOptions.overrideSplashText", "Splash Message");

        translationBuilder.add("text.smm-lib.error.worldnotfound.title", "World not found!");
        translationBuilder.add("text.smm-lib.error.worldnotfound.body", "World %s not found! Make sure it has been created first.");

    }
}
