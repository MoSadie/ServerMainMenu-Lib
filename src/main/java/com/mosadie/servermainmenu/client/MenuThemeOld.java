package com.mosadie.servermainmenu.client;

import java.util.Calendar;

public enum MenuThemeOld {
    NORMAL(-1, -1, "normal"),
    HALLOWEEN(Calendar.NOVEMBER, 9, "halloween"),
    WINTER(Calendar.DECEMBER, 25, "winter");

    private final int month;
    private final int day;
    private final String path;

    MenuThemeOld(int month, int day, String path) {
        this.month = month;
        this.day = day;
        this.path = path;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getPath() {
        return path;
    }
}
