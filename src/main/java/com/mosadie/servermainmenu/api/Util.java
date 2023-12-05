package com.mosadie.servermainmenu.api;

import com.mosadie.servermainmenu.client.ServerMainMenuLibClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.NoticeScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;

import java.time.LocalDate;

public class Util {
    /**
     * An implementation of rolling odds based on a month, day, and days before system.
     * Always returns false daysBefore days before the month/day
     * Then has an increasing chance to return true as the month/day approaches.
     * Returns true always on the month/day.
     * Has a decreasing chance over the daysAfter days after month/day
     * @param month Month of the year, with 1 = January, 2 = February, etc.
     * @param day The day of the month.
     * @param daysBefore Number of days before the month/day the theme should start appearing.
     * @param daysAfter Number of days after the month/day the theme should start appearing.
     * @return True if the theme should be selected, false otherwise. Usable in {@link MenuTheme#rollOdds}
     */
    public static boolean rollOddsMonthDay(int month, int day, int daysBefore, int daysAfter) {
        Random random = Random.create();

        LocalDate now = LocalDate.now();
        LocalDate themeDate = LocalDate.of(now.getYear(), month, day);
        LocalDate firstDate = themeDate.minusDays(daysBefore);
        LocalDate lastDate = themeDate.plusDays(daysAfter);

        // If we are before the first possible day, do not choose this theme.
        if (now.isBefore(firstDate))
            return false;

        // If we are after the last possible day, do not choose this theme.
        if (now.isAfter(lastDate))
            return false;


        // If it is the day listed, always attempt to choose the theme.
        if (now.equals(themeDate))
            return true;


        int roll;

        if (now.isBefore(themeDate)) {
            roll = random.nextBetween(0, daysBefore);
            return roll <= firstDate.until(now).getDays();
        } else {
            roll = random.nextBetween(0, daysAfter);
            return roll >= now.until(lastDate).getDays();
        }
    }

    /**
     * A simple rollOdds function that chooses the theme half the time.
     * @return True if the theme should be selected, false otherwise. Usable in {@link MenuTheme#rollOdds()}
     */
    public static boolean rollOddsFlipCoin() {
        Random random = Random.create();

        return random.nextBoolean();
    }

    /**
     * Connect to the specified server, defaulting the server name to the server address.
     * @param address The address of the server
     */
    public static void joinServer(String address) {
        joinServer(address, address);
    }

    /**
     * Connect to the specified server.
     * @param name Name of the server, used for mod data storage
     * @param address Address of the server
     */
    public static void joinServer(String name, String address) {
        if (ServerAddress.isValid(address)) {
            ServerInfo serverInfo = new ServerInfo(name, address, ServerInfo.ServerType.OTHER);
            serverInfo.setResourcePackPolicy(ServerInfo.ResourcePackPolicy.ENABLED);
            joinServer(serverInfo);
        }
    }

    /**
     * Directs Minecraft to connect to the specified server.
     * @param server The ServerInfo of the server to join.
     */
    public static void joinServer(ServerInfo server) {
        MinecraftClient.getInstance().send(() -> {
            leaveIfNeeded();

            ServerMainMenuLibClient.LOGGER.info("Connecting to " + server.address);

            // Connect to server

            ConnectScreen.connect(new TitleScreen(), MinecraftClient.getInstance(), ServerAddress.parse(server.address), server, false);
        });
    }

    public static void loadWorld(String worldName) {
        MinecraftClient.getInstance().send(() -> {
            if (MinecraftClient.getInstance().getLevelStorage().levelExists(worldName)) {
                leaveIfNeeded();

                ServerMainMenuLibClient.LOGGER.info("Loading world...");
                MinecraftClient.getInstance().createIntegratedServerLoader().start(new TitleScreen(), worldName);
            } else {
                ServerMainMenuLibClient.LOGGER.warn("World " + worldName + " does not exist!");
                if (MinecraftClient.getInstance().world == null)
                    MinecraftClient.getInstance().setScreen(new NoticeScreen(() -> MinecraftClient.getInstance().setScreen(new TitleScreen()), Text.translatable("text.smm-lib.error.worldnotfound.title"), Text.translatable("text.smm-lib.error.worldnotfound.body", worldName), ScreenTexts.TO_TITLE, true));
            }
        });
    }

    /**
     * Checks if in a world and leaves it.
     */
    private static void leaveIfNeeded() {
        if (MinecraftClient.getInstance().world != null) {
            ServerMainMenuLibClient.LOGGER.info("Disconnecting from world...");

            MinecraftClient.getInstance().world.disconnect();
            MinecraftClient.getInstance().disconnect();
        }
    }
}
