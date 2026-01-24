package com.mosadie.servermainmenu.client;

import com.mosadie.servermainmenu.api.MenuTheme;
import com.mosadie.servermainmenu.api.NormalTheme;
import com.mosadie.servermainmenu.api.Util;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class ServerMainMenuLibClient implements ClientModInitializer {

    public static final String MOD_ID = "smm-lib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    static RegistryKey<Registry<MenuTheme>> registryKey = RegistryKey.ofRegistry(Identifier.of(MOD_ID, "menu_theme"));
    public static Registry<MenuTheme> registry = FabricRegistryBuilder.createSimple(registryKey)
            .buildAndRegister();

    private static MenuTheme selectTheme() {
        LOGGER.info("Selecting Menu Theme!");

        MenuTheme selectedTheme = null;

        if (config != null && config.themeOptions.themeNamespace != null && config.themeOptions.themeId != null && config.themeOptions.overrideTheme) {
            LOGGER.info("Theme selected via override: " + config.themeOptions.themeNamespace + ":" + config.themeOptions.themeId);

            Identifier id = Identifier.of(config.themeOptions.themeNamespace, config.themeOptions.themeId);
            if (registry.containsId(id)) {
                selectedTheme = registry.get(id);
            }
            else {
                LOGGER.info("Failed to get theme via override! Falling back to Normal.");
                selectedTheme = new NormalTheme();
            }
        }

        if (selectedTheme == null) {
            LOGGER.info("Selecting random theme...");
            int currentPriority = 0;
            for (MenuTheme theme : registry) {
                if (theme.getPriority() >= currentPriority) {
                    if (theme.rollOdds()) {
                        selectedTheme = theme;
                        currentPriority = theme.getPriority();
                    }
                }
            }
        }

        if (selectedTheme == null) {
            LOGGER.info("No theme selected! Falling back to Normal.");
            selectedTheme = new NormalTheme();
        }

        LOGGER.info("Selected Menu Theme: " + selectedTheme.getId());
        return selectedTheme;
    }

    private static MenuTheme menuTheme = null;

    public static MenuTheme getTheme() {
        if (menuTheme != null) {
            return menuTheme;
        }

        menuTheme = selectTheme();
        return menuTheme;
    }

    private static ServerMainMenuLibConfig config;

    public static Text getSplashText() {
        if (config != null && config.splashOptions.overrideSplash) {
            return Text.of(config.splashOptions.overrideSplashText);
        }

        return Text.of(getTheme().getSplashText());
    }

    public static Text getButtonText() {
        if (config != null && config.quickJoinButtonOptions.overrideQuickJoinButton) {
            return Text.of(config.quickJoinButtonOptions.buttonTextOverride);
        }

        return getTheme().getQuickJoinButtonText();
    }

    public static void onQuickJoinClick() {
        if (config != null && config.quickJoinButtonOptions.overrideQuickJoinButton) {
            switch (config.quickJoinButtonOptions.buttonType) {
                case SERVER -> Util.joinServer(config.quickJoinButtonOptions.buttonNameOverride, config.quickJoinButtonOptions.buttonDestinationOverride);
                case WORLD -> Util.loadWorld(config.quickJoinButtonOptions.buttonDestinationOverride);
            }
            return;
        }

        getTheme().onQuickJoinClicked();
    }

    public static boolean isSingleplayerVisible() {
        if (config != null && config.visibilityOptions.singleplayer != ServerMainMenuLibConfig.VisibilityOptions.VisibilityState.DEFAULT) {
            return config.visibilityOptions.singleplayer == ServerMainMenuLibConfig.VisibilityOptions.VisibilityState.SHOW;
        }

        return getTheme().isSingleplayerVisible();
    }

    public static boolean isMultiplayerVisible() {
        if (config != null && config.visibilityOptions.multiplayer != ServerMainMenuLibConfig.VisibilityOptions.VisibilityState.DEFAULT) {
            return config.visibilityOptions.multiplayer == ServerMainMenuLibConfig.VisibilityOptions.VisibilityState.SHOW;
        }

        return getTheme().isMultiplayerVisible();
    }

    public static boolean isModsVisible() {
        if (config != null && config.visibilityOptions.mods != ServerMainMenuLibConfig.VisibilityOptions.VisibilityState.DEFAULT) {
            return config.visibilityOptions.mods == ServerMainMenuLibConfig.VisibilityOptions.VisibilityState.SHOW;
        }

        return getTheme().isModsVisible();
    }

    public static boolean isQuickJoinVisible() {
        if (config != null && config.visibilityOptions.quickJoin != ServerMainMenuLibConfig.VisibilityOptions.VisibilityState.DEFAULT) {
            return config.visibilityOptions.quickJoin == ServerMainMenuLibConfig.VisibilityOptions.VisibilityState.SHOW;
        }

        return getTheme().isQuickJoinVisible();
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing SimpleMainMenu-Lib...");

        LOGGER.info("Configuring Config...");

        AutoConfig.register(ServerMainMenuLibConfig.class, GsonConfigSerializer::new);

        AutoConfig.getConfigHolder(ServerMainMenuLibConfig.class).registerSaveListener(ServerMainMenuLibClient::onConfigSave);

        config = AutoConfig.getConfigHolder(ServerMainMenuLibConfig.class).getConfig();

        LOGGER.info("SimpleServerMenu-Lib Initialized!");
    }

    private static ActionResult onConfigSave(ConfigHolder<ServerMainMenuLibConfig> islandMenuConfigConfigHolder, ServerMainMenuLibConfig serverMainMenuLibConfig) {
        LOGGER.info("Updating config!");

        config = serverMainMenuLibConfig;

        menuTheme = selectTheme();

        return ActionResult.PASS;
    }
}
