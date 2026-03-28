package com.mosadie.simplemainmenu.api;


import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public interface MenuTheme {
    String getId();
    Identifier getPanorama();
    SplashText getSplashText();

    boolean rollOdds();

    int getPriority();

    // -- Quick Join Button ---
    Component getQuickJoinButtonComponent();
    void onQuickJoinClicked();

    // --- Button Visibility ---

    boolean isSingleplayerVisible();

    boolean isMultiplayerVisible();

    boolean isQuickJoinVisible();

    boolean isModsVisible();



}
