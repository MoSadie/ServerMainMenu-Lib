package com.mosadie.servermainmenu.client;

import com.mosadie.servermainmenu.api.MenuTheme;
import com.mosadie.servermainmenu.api.NormalTheme;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.client.network.ServerInfo;
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
    static RegistryKey<Registry<MenuTheme>> registryKey = RegistryKey.ofRegistry(new Identifier(MOD_ID, "menu_theme"));
    public static Registry<MenuTheme> registry = FabricRegistryBuilder.createSimple(registryKey)
            .buildAndRegister();

    private static MenuTheme selectTheme() {
        LOGGER.info("Selecting Menu Theme!");

        if (config != null && config.themeOptions.themeNamespace != null && config.themeOptions.themeId != null && config.themeOptions.overrideTheme) {
            LOGGER.info("Theme selected via override: " + config.themeOptions.themeNamespace + ":" + config.themeOptions.themeId);

            Identifier id = new Identifier(config.themeOptions.themeNamespace, config.themeOptions.themeId);

            if (registry.containsId(id))
                return registry.get(id);
            else {
                LOGGER.info("Failed to get theme via override! Falling back to Normal.");
                return new NormalTheme();
            }
        }

        MenuTheme selectedTheme = new NormalTheme();

        for(MenuTheme theme : registry) {
            if (theme.getPriority() >= selectedTheme.getPriority()) {
                if (theme.rollOdds()) {
                    selectedTheme = theme;
                }
            }
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

    public static String getSplashText() {
        if (config != null && config.splashOptions.overrideSplash) {
            return config.splashOptions.overrideSplashText;
        }

        return getTheme().getSplashText();
    }

    public static Text getButtonText() {
        if (config != null && config.joinButtonOptions.overrideJoinButton) {
            return Text.of(config.joinButtonOptions.buttonTextOverride);
        }

        return getTheme().getJoinServerButtonText();
    }

    public static ServerInfo getButtonServerInfo() {
        if (config != null && config.joinButtonOptions.overrideJoinButton) {
            ServerInfo serverInfo = new ServerInfo(config.joinButtonOptions.buttonServerNameOverride, config.joinButtonOptions.buttonServerAddressOverride, false);
            serverInfo.setResourcePackPolicy(ServerInfo.ResourcePackPolicy.ENABLED);
            return serverInfo;
        }

        return getTheme().getServerInfo();
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
