package com.mosadie.simplemainmenu.client;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = SimpleMainMenuLibClient.MOD_ID)
public class SimpleMainMenuLibConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    QuickJoinButtonOptions quickJoinButtonOptions = new QuickJoinButtonOptions();

    @ConfigEntry.Gui.CollapsibleObject
    VisibilityOptions visibilityOptions = new VisibilityOptions();

    @ConfigEntry.Gui.CollapsibleObject
    ThemeOptions themeOptions = new ThemeOptions();

    @ConfigEntry.Gui.CollapsibleObject
    SplashOptions splashOptions = new SplashOptions();


    static class QuickJoinButtonOptions {
        boolean overrideQuickJoinButton = false;
        String buttonTextOverride = "Join the game!";
        @ConfigEntry.Gui.Tooltip
        String buttonNameOverride = "Server Name";

        String buttonDestinationOverride = "localhost";

        QuickJoinButtonType buttonType = QuickJoinButtonType.SERVER;

        enum QuickJoinButtonType {
            SERVER,
            WORLD
        }
    }

    static class VisibilityOptions {
        enum VisibilityState {
            DEFAULT,
            SHOW,
            HIDE
        }

        VisibilityState singleplayer = VisibilityState.DEFAULT;

        VisibilityState multiplayer = VisibilityState.DEFAULT;

        VisibilityState mods = VisibilityState.DEFAULT;

        VisibilityState quickJoin = VisibilityState.DEFAULT;
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
