package dev.denismasterherobrine.subtaxes.cycles;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class FirstTimePlayerDateCheck {
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player pl = event.getPlayer();
        if (!pl.hasPlayedBefore()){
            int executingDayOfWeek = GetDate.getDayOfWeek();
            System.out.println(executingDayOfWeek);
            // Check for a day
            if (executingDayOfWeek == 1){
                boolean NO_TAXES = false;
            }
            if (executingDayOfWeek == 2){
                boolean NO_TAXES = false;
            }
            if (executingDayOfWeek == 3){
                boolean NO_TAXES = false;
            }
            if (executingDayOfWeek == 4){
                boolean NO_TAXES = true;
            }
            if (executingDayOfWeek == 5){
                boolean NO_TAXES = true;
            }
            if (executingDayOfWeek == 6){
                boolean NO_TAXES = true;
            }
            if (executingDayOfWeek == 7){
                boolean NO_TAXES = true;
            }
        }
    }
}
