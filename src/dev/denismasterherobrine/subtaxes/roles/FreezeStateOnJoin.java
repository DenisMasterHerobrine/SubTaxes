package dev.denismasterherobrine.subtaxes.roles;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class FreezeStateOnJoin {
    ArrayList<String> frozenPlayers = new ArrayList<>();

    String prefix = ChatColor.WHITE + "[" + ChatColor.BLUE + "Subserver Message" + ChatColor.WHITE + "] ";

    ChatColor red = ChatColor.RED;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (this.frozenPlayers.contains(p.getName())) {
            e.setTo(e.getFrom());
            p.sendMessage(String.valueOf(this.prefix) + this.red + "Вы не можете двигаться сейчас, поскольку не выбрали класс!");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (this.frozenPlayers.contains(p.getName())) {
            e.setCancelled(true);
            p.sendMessage(String.valueOf(this.prefix) + this.red + "Вы не можете ломать блоки, поскольку не выбрали класс!");
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (this.frozenPlayers.contains(p.getName())) {
            e.setCancelled(true);
            p.sendMessage(String.valueOf(this.prefix) + this.red + "Вы не можете ставить блоки, поскольку не выбрали класс!");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player pl = event.getPlayer();
        this.frozenPlayers.add(pl.getName());
        pl.setAllowFlight(true);
        pl.setFlying(true);
        if (this.frozenPlayers.contains(pl.getName())){
            RolesGUI rolesGUI = new RolesGUI();
            rolesGUI.openInventory(pl);
            pl.sendMessage("Если вы закрыли меню выбора классов, пропишите команду /roles select");
        }

    }
}
