package dev.denismasterherobrine.subtaxes.cycles;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GetDate {
    private static Date date = Calendar.getInstance(Locale.ENGLISH).getTime();
    private static int dayOfWeek = Calendar.DAY_OF_WEEK;

    public static Date getDate() {
        return date; // Wed Oct 31 11:44:02 CEST 2018 format
    }

    public static int getDayOfWeek() {
        return dayOfWeek;
    }
}
