package dev.denismasterherobrine.subtaxes.cycles;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class GetDate {
    public static Date getDate() {
        ZoneId zoneId = ZoneId.of("Europe/Moscow");
        return Calendar.getInstance(TimeZone.getTimeZone(zoneId), Locale.ENGLISH).getTime(); // Wed Oct 31 11:44:02 CEST 2018 format
    }

    public static int getDayOfWeek() {
        Instant instant = Instant.now();
        ZoneId zoneId = ZoneId.of("Europe/Moscow");
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zoneId);
        DayOfWeek dow = DayOfWeek.from(zdt);
        return dow.getValue();
    }
}
