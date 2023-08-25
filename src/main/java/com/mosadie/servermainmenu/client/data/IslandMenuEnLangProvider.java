package com.mosadie.servermainmenu.client.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class IslandMenuEnLangProvider extends FabricLanguageProvider {
    protected IslandMenuEnLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("island-menu.menu.join", "Join MCC Island!");

        translationBuilder.add("text.autoconfig.island-menu.title", "Island Menu Settings");

        translationBuilder.add("text.autoconfig.island-menu.option.supportingTeam", "Team to support");
        translationBuilder.add("text.autoconfig.island-menu.option.supportingTeam.@Tooltip", "Show your support for a team in the title screen's splash text, chooses the specified team instead of randomly picking a team when required.");

        translationBuilder.add("text.autoconfig.island-menu.option.joinButtonOptions", "Join Button Settings");

        translationBuilder.add("text.autoconfig.island-menu.option.joinButtonOptions.overrideJoinButton", "Override Join MCCi Button?");
        translationBuilder.add("text.autoconfig.island-menu.option.joinButtonOptions.buttonTextOverride", "Join Button Text");
        translationBuilder.add("text.autoconfig.island-menu.option.joinButtonOptions.buttonServerNameOverride", "Join Button Server Name");
        translationBuilder.add("text.autoconfig.island-menu.option.joinButtonOptions.buttonServerNameOverride.@Tooltip", "Mainly used by mods such as ReplayMod to track what server you were on.");
        translationBuilder.add("text.autoconfig.island-menu.option.joinButtonOptions.buttonServerAddressOverride", "Join Button Server Address");

        translationBuilder.add("text.autoconfig.island-menu.option.themeOptions", "Theme Settings");

        translationBuilder.add("text.autoconfig.island-menu.option.themeOptions.overrideTheme", "Override Menu Theme?");
        translationBuilder.add("text.autoconfig.island-menu.option.themeOptions.overrideTheme.@Tooltip", "Theme will update on next game launch.");
        translationBuilder.add("text.autoconfig.island-menu.option.themeOptions.theme", "Theme");

        translationBuilder.add("text.autoconfig.island-menu.option.splashOptions", "Splash Message Settings");

        translationBuilder.add("text.autoconfig.island-menu.option.splashOptions.overrideSplash", "Override Splash Text?");
        translationBuilder.add("text.autoconfig.island-menu.option.splashOptions.overrideSplashText", "Splash Message");

        translationBuilder.add("text.autoconfig.island-menu.option.devOptions", "Developer Settings");
        translationBuilder.add("text.autoconfig.island-menu.option.devOptions.apiUrl", "MCC API Base URL");

    }
}
