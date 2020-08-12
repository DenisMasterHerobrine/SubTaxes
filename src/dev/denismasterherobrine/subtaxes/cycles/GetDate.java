package dev.denismasterherobrine.subtaxes.cycles;

import java.util.Calendar;
import java.util.Date;

public class GetDate {
    Date date = Calendar.getInstance().getTime();

    public Date getDate() {
        return date; // Thu Mar 26 08:22:02 IST 2015 format
    }
}
