package com.mosadie.servermainmenu.client.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class IslandMenuHuLangProvider extends FabricLanguageProvider {
    protected IslandMenuHuLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "hu_hu");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("island-menu.menu.join", "Csatlakozz az MCC Islandre!");

        translationBuilder.add("text.autoconfig.island-menu.title", "Island Menu Beállítások");

        translationBuilder.add("text.autoconfig.island-menu.option.supportingTeam", "Támogatni kívánt csapat");
        translationBuilder.add("text.autoconfig.island-menu.option.supportingTeam.@Tooltip", "Mutassa ki egy csapat iránti elkötelezettségét a kezdőképernyőn egy felugró üzenetben, a megadott csapatot választja ki, ahelyett, hogy véletlenszerűen választana egy csapatot, amikor szükséges.");

        translationBuilder.add("text.autoconfig.island-menu.option.joinButtonOptions", "Csatlakozás Gomb Beállításai");

        translationBuilder.add("text.autoconfig.island-menu.option.joinButtonOptions.overrideJoinButton", "MCCi Csatlakozás Gomb Felülírása?");
        translationBuilder.add("text.autoconfig.island-menu.option.joinButtonOptions.buttonTextOverride", "Csatlakozás Gomb Szövege");
        translationBuilder.add("text.autoconfig.island-menu.option.joinButtonOptions.buttonServerNameOverride", "Csatlakozás Gomb Szerver Neve");
        translationBuilder.add("text.autoconfig.island-menu.option.joinButtonOptions.buttonServerNameOverride.@Tooltip", "Főleg az olyan modok használják, mint a ReplayMod, hogy nyomon kövessék, melyik szerveren voltál fent.");
        translationBuilder.add("text.autoconfig.island-menu.option.joinButtonOptions.buttonServerAddressOverride", "Csatlakozás Gomb Szerver Címe");

        translationBuilder.add("text.autoconfig.island-menu.option.themeOptions", "Téma Beállítások");

        translationBuilder.add("text.autoconfig.island-menu.option.themeOptions.overrideTheme", "Főmenü Téma Felülírása?");
        translationBuilder.add("text.autoconfig.island-menu.option.themeOptions.overrideTheme.@Tooltip", "A téma a következő játék indításakor frissül.");
        translationBuilder.add("text.autoconfig.island-menu.option.themeOptions.theme", "Téma");

        translationBuilder.add("text.autoconfig.island-menu.option.splashOptions", "Felugró Üzenetek Beállításai");

        translationBuilder.add("text.autoconfig.island-menu.option.splashOptions.overrideSplash", "Felugró Üzenetek Felülírása?");
        translationBuilder.add("text.autoconfig.island-menu.option.splashOptions.overrideSplashText", "Felugró Üzenetek");

        translationBuilder.add("text.autoconfig.island-menu.option.devOptions", "Fejlesztői Beállítások");
        translationBuilder.add("text.autoconfig.island-menu.option.devOptions.apiUrl", "MCC API Bázis URL-cím");
    }
}
