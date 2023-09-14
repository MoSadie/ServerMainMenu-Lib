package com.mosadie.servermainmenu.api;

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
}
