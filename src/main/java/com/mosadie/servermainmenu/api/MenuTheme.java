package com.mosadie.servermainmenu.api;

import net.minecraft.client.network.ServerInfo;
import net.minecraft.resource.Resource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public interface MenuTheme {
    String getId();
    Identifier getPanorama();
    String getSplashText();
    Text getJoinServerButtonText();
    ServerInfo getServerInfo();

    boolean rollOdds();

    int getPriority();
}
