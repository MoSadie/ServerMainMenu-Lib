package com.mosadie.servermainmenu.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * An example of a simple menu theme.
 */
public class NormalTheme implements MenuTheme{
    @Override
    public String getId() {
        return "normal";
    }

    @Override
    public Identifier getPanorama() {
        return new Identifier("minecraft", "textures/gui/title/background/panorama");
    }

    @Override
    public String getSplashText() {
        return "Just a normal menu...";
    }

    @Override
    public Text getJoinServerButtonText() {
        // Use Text.translatable for translatable text, or Text.literal to statically define text.
        return Text.translatable("text.ssmlib.normaltheme.joinserver");
    }

    @Override
    public ServerInfo getServerInfo() {
        // Remember the server name is used for mods such as minimaps and Replay Mod for data storage,
        // so it *may* be shown to the player and *should* be constant!
        return new ServerInfo("Localhost", "localhost", false);
    }

    @Override
    public boolean rollOdds() {
        // This method is used to determine if this theme should be shown,
        // an example of this being able to show a holiday theme as a holiday approaches
        // for a low-code example, you can just call any of the methods starting with "rollOdds" in my Util class.

        // Normal Theme always fail the odds roll. Your theme should not always return false. (Otherwise it won't get picked!)
        return false;
    }

    @Override
    public int getPriority() {
        // Higher values take priority over lower values.
        return -1;
    }
}
