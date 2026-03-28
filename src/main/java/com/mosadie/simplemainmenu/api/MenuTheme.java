package com.mosadie.simplemainmenu.api;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public interface MenuTheme {
    String getId();
    Identifier getPanorama();
    SplashText getSplashText();

    boolean rollOdds();

    int getPriority();

    // -- Quick Join Button ---
    Text getQuickJoinButtonText();
    void onQuickJoinClicked();

    // --- Button Visibility ---

    boolean isSingleplayerVisible();

    boolean isMultiplayerVisible();

    boolean isQuickJoinVisible();

    boolean isModsVisible();



}
