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
    public Text getQuickJoinButtonText() {
        // Use Text.translatable for translatable text, or Text.literal to statically define text.
        return Text.translatable("text.smmlib.normaltheme.joinserver");
    }

    @Override
    public void onQuickJoinClicked() {
        // For this example we join the server "localhost" on click.
        // To load a world, see Util.loadWorld

        Util.joinServer("Localhost", "localhost");
    }

    @Override
    public boolean isSingleplayerVisible() {
        // Determines the visibility of the "Singleplayer" button on the Title Screen.
        return true;
    }

    @Override
    public boolean isMultiplayerVisible() {
        // Determines the visibility of the "Multiplayer" button on the Title Screen.
        return true;
    }

    @Override
    public boolean isQuickJoinVisible() {
        // Determines the visibility of the Quick Join button on the Title Screen.
        return true;
    }

    @Override
    public boolean isModsVisible() {
        // Determines the visibility of the "Mods" button on the Title Screen.
        return true;
    }

    @Override
    public boolean rollOdds() {
        // This method is used to determine if this theme should be shown,
        // an example of this being able to show a holiday theme as a holiday approaches
        // for a low-code example, you can just call any of the methods starting with "rollOdds" in the Util class.

        // Normal Theme always fail the odds roll. Your theme should not always return false. (Otherwise it won't get picked!)
        return false;
    }

    @Override
    public int getPriority() {
        // Higher values take priority over lower values.
        return -1;
    }
}
