package dev.denismasterherobrine.subtaxes.cycles;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class FirstTimePlayerDateCheck {
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player pl = event.getPlayer();
        if (!pl.hasPlayedBefore()){
            Date PlayerJoinDate = GetDate.getDate();
            SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
            String PlayerJoinDateString = formatter.format(PlayerJoinDate);
            String[] PlayerJoinDateArrayListed = PlayerJoinDateString.split("\\s+");
            System.out.println(PlayerJoinDate);
            System.out.println(PlayerJoinDateString);
            System.out.println(Arrays.toString(PlayerJoinDateArrayListed));
            // Check for a day
            if (PlayerJoinDateArrayListed[0].equals("Mon")){
                boolean NO_TAXES = false;
            }
            if (PlayerJoinDateArrayListed[0].equals("Tue")){
                boolean NO_TAXES = false;
            }
            if (PlayerJoinDateArrayListed[0].equals("Wed")){
                boolean NO_TAXES = false;
            }
            if (PlayerJoinDateArrayListed[0].equals("Thu")){
                boolean NO_TAXES = true;
            }
            if (PlayerJoinDateArrayListed[0].equals("Fri")){
                boolean NO_TAXES = true;
            }
            if (PlayerJoinDateArrayListed[0].equals("Sat")){
                boolean NO_TAXES = true;
            }
            if (PlayerJoinDateArrayListed[0].equals("Sun")){
                boolean NO_TAXES = true;
            }
        }
    }
}
