package dev.denismasterherobrine.subtaxes.punishments;

import dev.denismasterherobrine.subtaxes.SubTaxes;
import org.bukkit.entity.Player;

public class HeartManager {
    public void removeHearts(Player player){
        int PlayerHP = SubTaxes.getPlugin(SubTaxes.class).getPlayerHPInt();
        int PlayerHPRemove = SubTaxes.getPlugin(SubTaxes.class).getPlayerHPRemove();
        player.setHealth(PlayerHP - PlayerHPRemove);
    }
    public void addHearts(Player player){
        int PlayerHP = SubTaxes.getPlugin(SubTaxes.class).getPlayerHPInt();
        int PlayerHPAdd = SubTaxes.getPlugin(SubTaxes.class).getPlayerHPAdd();
        player.setHealth(PlayerHP + PlayerHPAdd);
    }
}

