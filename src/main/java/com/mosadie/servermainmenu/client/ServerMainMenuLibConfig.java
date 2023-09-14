package com.mosadie.servermainmenu.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = ServerMainMenuLibClient.MOD_ID)
public class ServerMainMenuLibConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    JoinButtonOptions joinButtonOptions = new JoinButtonOptions();

    @ConfigEntry.Gui.CollapsibleObject
    ThemeOptions themeOptions = new ThemeOptions();

    @ConfigEntry.Gui.CollapsibleObject
    SplashOptions splashOptions = new SplashOptions();


    static class JoinButtonOptions {
        boolean overrideJoinButton = false;
        String buttonTextOverride = "Join the server!";
        @ConfigEntry.Gui.Tooltip
        String buttonServerNameOverride = "Server Name";
        String buttonServerAddressOverride = "localhost";
    }

    static class ThemeOptions {
        @ConfigEntry.Gui.Tooltip()
        boolean overrideTheme = false;
        String themeNamespace = "ssmlib";
        String themeId = "normal";
    }

    static class SplashOptions {
        boolean overrideSplash = false;
        String overrideSplashText = "Splash!";
    }
}
