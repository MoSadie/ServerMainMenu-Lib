package com.mosadie.servermainmenu.api;

import net.minecraft.client.network.ServerInfo;
import net.minecraft.resource.Resource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public interface MenuTheme {
    String getId();
    Identifier getPanorama();
    @Deprecated
    default String getSplashText() {
        return null;
    }
    Text getSplashAsText();

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
