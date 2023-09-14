package com.mosadie.servermainmenu.client.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ServerMainMenuLibEnLangProvider extends FabricLanguageProvider {
    protected ServerMainMenuLibEnLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("text.ssmlib.normaltheme.joinserver", "Join the server!");

        translationBuilder.add("text.autoconfig.smm-lib.title", "ServerMainMenu Lib Settings");

        translationBuilder.add("text.autoconfig.smm-lib.option.joinButtonOptions", "Join Button Settings");

        translationBuilder.add("text.autoconfig.smm-lib.option.joinButtonOptions.overrideJoinButton", "Override Join Server Button?");
        translationBuilder.add("text.autoconfig.smm-lib.option.joinButtonOptions.buttonTextOverride", "Join Button Text");
        translationBuilder.add("text.autoconfig.smm-lib.option.joinButtonOptions.buttonServerNameOverride", "Join Button Server Name");
        translationBuilder.add("text.autoconfig.smm-lib.option.joinButtonOptions.buttonServerNameOverride.@Tooltip", "Mainly used by mods such as ReplayMod to track what server you were on.");
        translationBuilder.add("text.autoconfig.smm-lib.option.joinButtonOptions.buttonServerAddressOverride", "Join Button Server Address");

        translationBuilder.add("text.autoconfig.smm-lib.option.themeOptions", "Theme Settings");

        translationBuilder.add("text.autoconfig.smm-lib.option.themeOptions.overrideTheme", "Override Menu Theme?");
        translationBuilder.add("text.autoconfig.smm-lib.option.themeOptions.overrideTheme.@Tooltip", "Theme will update on next game launch.");
        translationBuilder.add("text.autoconfig.smm-lib.option.themeOptions.themeNamespace", "Theme Namespace");
        translationBuilder.add("text.autoconfig.smm-lib.option.themeOptions.themeId", "Theme ID");

        translationBuilder.add("text.autoconfig.smm-lib.option.splashOptions", "Splash Message Settings");

        translationBuilder.add("text.autoconfig.smm-lib.option.splashOptions.overrideSplash", "Override Splash Text?");
        translationBuilder.add("text.autoconfig.smm-lib.option.splashOptions.overrideSplashText", "Splash Message");

    }
}
