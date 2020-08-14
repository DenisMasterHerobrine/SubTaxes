package dev.denismasterherobrine.subtaxes.roles;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class RolesGUI implements Listener {
    public final Inventory inv;

    public RolesGUI() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, 4, "-- Выбор класса --");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        inv.addItem(createGuiItem(Material.WHEAT, "Класс: Фермер", "§aВы чувствуете, что грядки это ваша стихия? Это ваше родное дело?", "§bТогда Фермер - это ваш выбор! Задания связанные с растительностью, грядками и прочее."));
        inv.addItem(createGuiItem(Material.GOLDEN_PICKAXE, "Класс: Шахтёр", "§aВам охото постоянно возиться в камнях и находить приятные ценности?", "§bТогда Шахтёр - это ваша профессия! Задания и налоги связаны с копанием ресурсов, добычей всего вкусного, что находится под землёй!"));
        inv.addItem(createGuiItem(Material.GOLDEN_SWORD, "Класс: Воин", "§aВы любите сражаться?", "§bТогда Воин - это ваше призвание. Сражайтесь против монстров и приносите лут! Эпичные сражения приносят пользу и процветание!"));
        inv.addItem(createGuiItem(Material.GOLDEN_HELMET, "§bКласс: Охотник", "§aВы любите охотиться за свинками, коровками или овцами?", "§bТогда Охотник - это то, что вам нужно. Эта профессия поможет всем, будете снабжать едой и разводить животных для восстановления их популяции по всему земному кубу!"));

    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        assert meta != null; // thx Spigot for not already checking this.
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() != inv)
            return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR)
            return;

        final Player p = (Player) e.getWhoClicked();
        Player player = (Player) e.getWhoClicked();
        ItemStack clicked = e.getCurrentItem();
        Inventory inventory = e.getInventory();
        if (inv.getSize() == 4) {
            if (clicked.getType() == Material.WHEAT){
                p.sendMessage("Вы выбрали класс Фермера!");
                FreezeStateOnJoin freezeStateOnJoin = new FreezeStateOnJoin();
                freezeStateOnJoin.frozenPlayers.remove(player);
                /*
                Запись в бд
                 */
            }
            if (clicked.getType() == Material.GOLDEN_PICKAXE){
                p.sendMessage("Вы выбрали класс Шахтёра!");
                FreezeStateOnJoin freezeStateOnJoin = new FreezeStateOnJoin();
                freezeStateOnJoin.frozenPlayers.remove(player);
                /*
                Запись в бд
                 */
            }
            if (clicked.getType() == Material.GOLDEN_SWORD){
                p.sendMessage("Вы выбрали класс Воина!");
                FreezeStateOnJoin freezeStateOnJoin = new FreezeStateOnJoin();
                freezeStateOnJoin.frozenPlayers.remove(player);
                /*
                Запись в бд
                 */
            }
            if (clicked.getType() == Material.GOLDEN_HELMET){
                p.sendMessage("Вы выбрали класс Охотника!");
                FreezeStateOnJoin freezeStateOnJoin = new FreezeStateOnJoin();
                freezeStateOnJoin.frozenPlayers.remove(player);
                /*
                Запись в бд
                 */
            }
        }

    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory() == inv) {
            e.setCancelled(true);
        }
    }
}
