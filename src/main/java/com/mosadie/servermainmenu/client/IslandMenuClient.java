package com.mosadie.servermainmenu.client;

import com.mosadie.servermainmenu.api.MenuTheme;
import com.mosadie.servermainmenu.mccapi.EventInfo;
import com.mosadie.servermainmenu.mccapi.MCCApi;
import com.mosadie.servermainmenu.mccapi.ParticipantsInfo;
import com.mosadie.servermainmenu.mccapi.Teams;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class IslandMenuClient implements ClientModInitializer {

    public static final String MOD_ID = "servermainmenu-lib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

//    public static final Registry<MenuTheme> menuThemeRegistry = new SimpleRegistry<>()
    RegistryKey<Registry<String>> registryKey = RegistryKey.ofRegistry(new Identifier(MOD_ID, "menu_theme"));
    Registry<String> registry = FabricRegistryBuilder.createSimple(registryKey)
            .buildAndRegister();

    private static MenuThemeOld selectTheme() {
        LOGGER.info("Selecting Menu Theme!");

        if (config != null && config.themeOptions.overrideTheme) {
            LOGGER.info("Theme selected via override: " + config.themeOptions.theme.name());
            return config.themeOptions.theme;
        }

        Calendar calendar = Calendar.getInstance();

        MenuThemeOld selectedTheme = MenuThemeOld.NORMAL;

        Random random = Random.create();


        for(MenuThemeOld theme : MenuThemeOld.values()) {
            if (calendar.get(Calendar.MONTH) == theme.getMonth() && calendar.get(Calendar.DAY_OF_MONTH) <= theme.getDay()) {
                LOGGER.info("Possible Theme Found: " + theme.name());

                int roll = random.nextBetween(1, theme.getDay());

                // If the roll is less than the day of the month, set as the theme to select.
                // The goal is as the dates get closer, the theme appears more often.
                if (roll <= calendar.get(Calendar.DAY_OF_MONTH)) {
                    LOGGER.info("Successfully rolled " + roll + "/" + theme.getDay() + " (compared to " + calendar.get(Calendar.DAY_OF_MONTH) + ") for theme");
                    selectedTheme = theme;
                } else {
                    LOGGER.info("Failed rolled " + roll + "/" + theme.getDay() + " (compared to  " + calendar.get(Calendar.DAY_OF_MONTH) + ") for theme");
                }
            }
        }

        LOGGER.info("Selected Menu Theme: " + selectedTheme.name());
        return selectedTheme;
    }

    private static MenuThemeOld menuThemeOld = null;

    public static MenuThemeOld getTheme() {
        if (menuThemeOld != null) {
            return menuThemeOld;
        }

        return MenuThemeOld.NORMAL;
    }

    private final static String MCC_DATE_SPLASH = "island-menu.mccdatesplash";
    private final static String MCC_PLAYER_SPLASH = "island-menu.playersplash";

    private final static String MCC_TEAM_SPLASH = "island-menu.teamsplash";

    private final static String[] splashOptions = {
            "Set Sail Today!",
            "Enjoy your island vacation!",
            "Get the latest news at mccisland.net!",
            "See live MCC stats at mcc.live!",
            "Support the Noxcrew!",
            MCC_TEAM_SPLASH,
            MCC_DATE_SPLASH,
            MCC_PLAYER_SPLASH

    };

    private final static String[] teamSplashOptions = {
            "Go %s!",
            "Support the %s!"
    };

    public static String getSplashText() {
        if (config != null && config.splashOptions.overrideSplash) {
            return config.splashOptions.overrideSplashText;
        }
        String splash =  splashOptions[Random.create().nextBetweenExclusive(0, splashOptions.length)];

        switch(splash) {
            case MCC_DATE_SPLASH:
                if (mccApi != null) {
                    EventInfo eventInfo = mccApi.getEventInfo();
                    if (eventInfo != null) {
                        if (eventInfo.getData().getDate().after(new Date())) {
                            String dateString = new SimpleDateFormat("MMMM dd").format(eventInfo.getData().getDate());
                            return String.format("Watch MCC %s on %s!", eventInfo.getData().getEvent(), dateString);
                        } else {
                            return String.format("What did you think of MCC %s?", eventInfo.getData().getEvent());
                        }
                    }
                }
                return splashOptions[0];

            case MCC_PLAYER_SPLASH:
                if (mccApi != null) {
                    ParticipantsInfo participantsInfo = mccApi.getParticipantsInfo();

                    if (participantsInfo != null) {
                        ParticipantsInfo.ParticipantsData.Participant player = null;

                        ParticipantsInfo.ParticipantsData.Participant selfPlayer = participantsInfo.getPlayer(MinecraftClient.getInstance().getSession().getUuidOrNull());
                        if (selfPlayer != null) {
                            LOGGER.debug("MCC Player found! Picking them always for splash text.");
                            player = selfPlayer;
                        } else {
                            List<Teams> teams = new java.util.ArrayList<>(List.of(Teams.values().clone()));
                            Collections.shuffle(teams);

                            for (Teams team : teams) {
                                if (team.equals(Teams.NONE) || team.equals(Teams.SPECTATORS)) {
                                    continue;
                                }

                                ParticipantsInfo.ParticipantsData.Participant[] members = participantsInfo.getData().getTeam(team);

                                if (members.length != 0) {
                                    int memberIndex = RandomUtils.nextInt(0, members.length);
                                    player = members[memberIndex];
                                    break;
                                }
                            }

                            if (player == null)
                                return splashOptions[0];
                        }

                        if (player == null || player.getUsername() == null)
                            return splashOptions[0];

                        EventInfo eventInfo = mccApi.getEventInfo();
                        String eventString = "";
                        if (eventInfo != null && eventInfo.getData().getDate().after(new Date())) {
                            eventString = " in MCC " + eventInfo.getData().getEvent();
                        }
                        return String.format("Check out %s!", player.getUsername() + eventString);
                    }
                }

                return splashOptions[0];

            case MCC_TEAM_SPLASH:
                Teams team = null;

                if (config != null) {
                    team = config.supportingTeam;
                }

                // If player is in the next/most recent MCC, pick their team.
                if ((team == null || team.equals(Teams.NONE)) && mccApi != null && mccApi.getParticipantsInfo() != null && MinecraftClient.getInstance().getSession().getUuidOrNull() != null) {
                    Teams selfTeam = mccApi.getParticipantsInfo().getPlayerTeam(MinecraftClient.getInstance().getSession().getUuidOrNull());
                    if (!selfTeam.equals(Teams.NONE)) {
                        LOGGER.debug("MCC Player team found!");
                        team = selfTeam;
                    }
                }

                while(team == null || team.equals(Teams.NONE) || (team.equals(Teams.SPECTATORS) && !config.supportingTeam.equals(Teams.SPECTATORS))) {
                    team = Teams.values()[RandomUtils.nextInt(0, Teams.values().length)];
                }

                String teamString = teamSplashOptions[RandomUtils.nextInt(0, teamSplashOptions.length)];

                return String.format(teamString, team.getName());

            default:
                return splash;
        }
    }

    private static IslandMenuConfig config;

    public static Text getButtonText() {
        if (config != null && config.joinButtonOptions.overrideJoinButton) {
            return Text.of(config.joinButtonOptions.buttonTextOverride);
        }

        return Text.translatable("island-menu.menu.join");
    }

    public static String getButtonServerName() {
        if (config != null && config.joinButtonOptions.overrideJoinButton) {
            return config.joinButtonOptions.buttonServerNameOverride;
        }

        return "MCC Island";
    }

    public static String getButtonServerAddress() {
        if (config != null && config.joinButtonOptions.overrideJoinButton) {
            return config.joinButtonOptions.buttonServerAddressOverride;
        }

        return "play.mccisland.net";
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Island Menu...");

        LOGGER.info("Configuring Config...");

        AutoConfig.register(IslandMenuConfig.class, GsonConfigSerializer::new);

        AutoConfig.getConfigHolder(IslandMenuConfig.class).registerSaveListener(IslandMenuClient::onConfigSave);

        config = AutoConfig.getConfigHolder(IslandMenuConfig.class).getConfig();

        menuThemeOld = selectTheme();

        LOGGER.info("Requesting information from MCC API...");

        mccApi = new MCCApi(config.devOptions.apiUrl);

        // Calls to make the cache when minor lag is fine.
        mccApi.getEventInfo();
        mccApi.getParticipantsInfo();


        LOGGER.info("Island Menu Initialized!");
    }

    private static ActionResult onConfigSave(ConfigHolder<IslandMenuConfig> islandMenuConfigConfigHolder, IslandMenuConfig islandMenuConfig) {
        LOGGER.info("Updating config!");

//        config = islandMenuConfig;

        menuThemeOld = selectTheme();

        if (mccApi == null || !mccApi.getBaseUrl().equalsIgnoreCase(config.devOptions.apiUrl))
            mccApi = new MCCApi(config.devOptions.apiUrl);

        return ActionResult.PASS;
    }
}
