package dev.denismasterherobrine.subtaxes.commands.farms;

import dev.denismasterherobrine.subtaxes.SubTaxes;
import dev.denismasterherobrine.subtaxes.utils.Triplet;
import dev.denismasterherobrine.subtaxes.utils.Utils;
import dev.denismasterherobrine.subtaxes.cycles.GetDate;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.*;

public class FarmsCommand implements CommandExecutor {
    private Connection connection;

    private Map<String, Triplet<Player, Player, Integer>> transfer = new HashMap<>();

    private boolean isInTransfer(String id){
        synchronized (transfer) {
            return transfer.get(id) != null;
        }
    }

    public FarmsCommand(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SubTaxes.getPlugin(SubTaxes.class), ()->{
            Iterator i = transfer.entrySet().iterator();
            while (i.hasNext()){
                Map.Entry<String, Triplet<Player, Player, Integer>> entry = (Map.Entry<String, Triplet<Player, Player, Integer>>) i.next();
                if(entry.getValue().c > 30){
                    entry.getValue().a.sendMessage("§6Игрок §f" + entry.getValue().b.getName() + " §6так и не принял вашу заявку.");
                    entry.getValue().b.sendMessage("§6Заявка на передачу фермы игрока §f" + entry.getValue().a.getName() + " §6истекла.");
                    i.remove();
                }
                else {
                    entry.getValue().c++;
                }
            }
        }, 20l, 20l);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("farms")) {
            if (commandSender instanceof Player) {
                if (strings.length == 0) {
                    printFarmList((Player) commandSender);
                    return false;
                }
            }

            // /farms remove
            if (strings.length == 1) {
                if (strings[0].equals("remove")) {
                    Player player = (Player) commandSender;
                    int executingDayOfWeek = GetDate.getDayOfWeek();
                    if (executingDayOfWeek != 7) {
                        player.sendMessage("§6Укажите ID фермы, которую хотите удалить! (Пример: /farms remove AAA)");
                        return true;
                    } else player.sendMessage("§cДанная команда работает во все дни, кроме воскресенья!");
                }
            }
            // /farms add
            if (strings.length == 1) {
                if (strings[0].equals("add")) {
                    if (commandSender instanceof Player) {
                        Player player = (Player) commandSender;
                        int executingDayOfWeek = GetDate.getDayOfWeek();
                        if (executingDayOfWeek != 7) {
                            player.sendMessage("§6Укажите название фермы! Например: /farms add Ферма железа");
                            return true;
                        } else player.sendMessage("§cДанная команда работает во все дни, кроме воскресенья!");
                    }
                }
            }
        }

        // /farms add <id> decline
        if (command.getName().equalsIgnoreCase("farms")) {
            if (strings.length == 3) {
                if (strings[0].equals("add")) {
                    if (strings[1] != null) {
                        if (strings[2].equals("decline")) {
                            Player player = ((Player) commandSender);
                            int executingDayOfWeek = GetDate.getDayOfWeek();
                            if (executingDayOfWeek != 7) {
                                if(strings[1].matches("[A-Z]*")) {
                                    if(isInTransfer(strings[1])){
                                        player.sendMessage("§cОшибка: Ферма находиться в процессе передачи другому игроку.");
                                        return true;
                                    }
                                    BukkitRunnable r = new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                openConnection();
                                                Statement statement = connection.createStatement();
                                                ResultSet result = statement.executeQuery("SELECT * FROM farms WHERE Type=1 AND Owner='" + player.getUniqueId().toString() + "' AND ID='" + strings[1] +"';");
                                                if (result.next()) {
                                                    String SQLString = "DELETE FROM farms WHERE Type=1 AND Owner='" + player.getUniqueId().toString() + "' AND ID='" + strings[1] +"';";
                                                    statement.executeUpdate(SQLString);
                                                    player.sendMessage("§6Ваша ферма успешно удалена из списка на рассмотрение!");
                                                    printFarmList(player);
                                                }
                                                else player.sendMessage("§cОшибка: запись не найдена!");
                                                connection.close();
                                            } catch (ClassNotFoundException | SQLException e) {
                                                e.printStackTrace();
                                                player.sendMessage("§cПроизошла ошибка, попробуйте позже!");
                                            }
                                        }
                                    };
                                    r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                                }
                                else player.sendMessage("§cНекорректный ID!");
                            } else player.sendMessage("§cДанная команда работает во все дни, кроме воскресенья!");
                            return true;
                        }
                    }
                }
            }
        }

        // /farms add <name>
        if (command.getName().equalsIgnoreCase("farms")) {
            if (strings.length >= 2) {
                if (strings[0].equals("add")) {
                    if (strings[1] != null) {
                        String newName = strings[1];
                        for (int i = 2; i < strings.length; i++)
                            newName += " " + strings[i];
                        Player player = ((Player) commandSender);
                        int executingDayOfWeek = GetDate.getDayOfWeek();
                        if (executingDayOfWeek != 7) {
                            if(newName.matches("^[A-Za-z А-Яа-я1-9.,/\\-\\*\\+()\\[\\]]*")) {
                                String world = player.getWorld().getName();
                                Location loc = player.getLocation();
                                double x = loc.getX();
                                double y = loc.getY();
                                double z = loc.getZ();
                                UUID PlayerUUID = player.getUniqueId();
                                System.out.println("Player Add Info: " + world + " " + x + " " + y + " " + z + ".");
                                String finalNewName = newName;
                                BukkitRunnable r = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            openConnection();
                                            Statement statement = connection.createStatement();
                                            String SQLString = "INSERT INTO farms (Type, Owner, World, X, Y, Z, Name, ID) VALUES (1, '" + PlayerUUID + "', '" + world + "', " + x + ", " + y + ", " + z + ", '" + finalNewName + "', '" + Utils.getUinqueId(3) + "');";
                                            statement.executeUpdate(SQLString);
                                            connection.close();
                                            player.sendMessage("§6Ваша ферма успешно добавлена в список на рассмотрение!");
                                            printFarmList((Player) commandSender);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            player.sendMessage("§cПроизошла ошибка, попробуйте позже!");
                                        }
                                    }
                                };
                                r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                                return true;
                            } else player.sendMessage("§6В названии допустимы только русские и английские буквы, цифры, а так же символы: .,/-*+()[]");
                        } else player.sendMessage("§cДанная команда работает во все дни, кроме воскресенья!");
                    }
                }
            }
        }

        // /farms rename
        if (command.getName().equalsIgnoreCase("farms")) {
            if (strings.length == 1) {
                if (strings[0].equals("rename")) {
                    Player player = ((Player) commandSender);
                    int executingDayOfWeek = GetDate.getDayOfWeek();
                    if (executingDayOfWeek != 7) {
                        player.sendMessage("§6Укажите ID и новое название фермы! (Пример: /farms rename AAA Новое название)");
                    } else player.sendMessage("§cДанная команда работает во все дни, кроме воскресенья!");
                }
            }
        }

        // /farms rename <id>
        if (command.getName().equalsIgnoreCase("farms")) {
            if (strings.length == 2) {
                if (strings[0].equals("rename")) {
                    if (strings[1] != null) {
                        Player player = ((Player) commandSender);
                        int executingDayOfWeek = GetDate.getDayOfWeek();
                        if (executingDayOfWeek != 7) {
                            player.sendMessage("§6Укажите новое название фермы! (Пример: /farms rename AAA Новое название)");
                        } else player.sendMessage("§cДанная команда работает во все дни, кроме воскресенья!");
                    }
                }
            }
        }

        // /farms rename <id> <name>
        if (command.getName().equalsIgnoreCase("farms")) {
            if (strings.length >= 3) {
                if (strings[0].equals("rename")) {
                    if (strings[1] != null) {
                        String newName = strings[2];
                        for (int i = 3; i < strings.length; i++)
                            newName += " " + strings[i];
                        Player player = ((Player) commandSender);
                        int executingDayOfWeek = GetDate.getDayOfWeek();
                        if (executingDayOfWeek != 7) {
                            if(strings[1].matches("[A-Z]*")) {
                                if (newName.matches("^[A-Za-z А-Яа-я1-9.,/\\-\\*\\+()\\[\\]]*")) {
                                    if (isInTransfer(strings[1])) {
                                        player.sendMessage("§cОшибка: Ферма находиться в процессе передачи другому игроку.");
                                        return true;
                                    }
                                    String finalNewName = newName;
                                    BukkitRunnable r = new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                openConnection();
                                                Statement statement = connection.createStatement();
                                                ResultSet result = statement.executeQuery("SELECT * FROM farms WHERE Type=0 AND Owner='" + player.getUniqueId().toString() + "' AND ID='" + strings[1] + "';");
                                                if (result.next()) {
                                                    String SQLString = "UPDATE farms SET Name='" + finalNewName + "' WHERE Type=0 AND Owner='" + player.getUniqueId().toString() + "' AND ID='" + strings[1] + "';";
                                                    statement.executeUpdate(SQLString);
                                                    player.sendMessage("§6Ваша ферма успешно переименована!");
                                                    printFarmList(player);
                                                } else player.sendMessage("§cОшибка: запись не найдена!");
                                                connection.close();
                                            } catch (ClassNotFoundException | SQLException e) {
                                                e.printStackTrace();
                                                player.sendMessage("§cПроизошла ошибка, попробуйте позже!");
                                            }
                                        }
                                    };
                                    r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                                } else player.sendMessage("§6В названии допустимы только русские и английские буквы, цифры, а так же символы: .,/-*+()[]");
                            }
                            else player.sendMessage("§cНекорректный ID!");
                        } else player.sendMessage("§cДанная команда работает во все дни, кроме воскресенья!");
                    }
                }
            }
        }

        // /farms remove <id>
        if (command.getName().equalsIgnoreCase("farms")) {
            if (strings.length == 2) {
                if (strings[0].equals("remove")) {
                    if (strings[1] != null) {
                        Player player = ((Player) commandSender);
                        int executingDayOfWeek = GetDate.getDayOfWeek();
                        if (executingDayOfWeek != 7) {
                            if(strings[1].matches("[A-Z]*")) {
                                if(isInTransfer(strings[1])){
                                    player.sendMessage("§cОшибка: Ферма находиться в процессе передачи другому игроку.");
                                    return true;
                                }
                                BukkitRunnable r = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            openConnection();
                                            Statement statement = connection.createStatement();
                                            ResultSet result = statement.executeQuery("SELECT * FROM farms WHERE Type=0 AND Owner='" + player.getUniqueId().toString() + "' AND ID='" + strings[1] +"';");
                                            if (result.next()) {
                                                String SQLString = "UPDATE farms SET Type=2 WHERE Type=0 AND Owner='" + player.getUniqueId().toString() + "' AND ID='" + strings[1] +"';";
                                                statement.executeUpdate(SQLString);
                                                player.sendMessage("§6Ваша ферма успешно добавлена в список на удаление!");
                                                printFarmList(player);
                                            }
                                            else player.sendMessage("§cОшибка: запись не найдена!");
                                            connection.close();
                                        } catch (ClassNotFoundException | SQLException e) {
                                            e.printStackTrace();
                                            player.sendMessage("§cПроизошла ошибка, попробуйте позже!");
                                        }
                                    }
                                };
                                r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                            }
                            else player.sendMessage("§cНекорректный ID!");
                        } else player.sendMessage("§cДанная команда работает во все дни, кроме воскресенья!");
                }
                }
            }
        }

        // /farms remove <id> decline
        if (command.getName().equalsIgnoreCase("farms")) {
            if (strings.length == 3) {
                if (strings[0].equals("remove")) {
                    if (strings[1] != null) {
                        if (strings[2].equals("decline")) {
                            Player player = ((Player) commandSender);
                            int executingDayOfWeek = GetDate.getDayOfWeek();
                            if (executingDayOfWeek != 7) {
                                if(strings[1].matches("[A-Z]*")) {
                                    if(isInTransfer(strings[1])){
                                        player.sendMessage("§cОшибка: Ферма находиться в процессе передачи другому игроку.");
                                        return true;
                                    }
                                    BukkitRunnable r = new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                openConnection();
                                                Statement statement = connection.createStatement();
                                                ResultSet result = statement.executeQuery("SELECT * FROM farms WHERE Type=2 AND Owner='" + player.getUniqueId().toString() + "' AND ID='" + strings[1] +"';");
                                                if (result.next()) {
                                                    String SQLString = "UPDATE farms SET Type=0 WHERE Type=2 AND Owner='" + player.getUniqueId().toString() + "' AND ID='" + strings[1] +"';";
                                                    statement.executeUpdate(SQLString);
                                                    player.sendMessage("§6Ваша ферма убрана из списка на удаление!");
                                                    printFarmList(player);
                                                }
                                                else player.sendMessage("§cОшибка: запись не найдена!");
                                                connection.close();
                                            } catch (ClassNotFoundException | SQLException e) {
                                                e.printStackTrace();
                                                player.sendMessage("§cПроизошла ошибка, попробуйте позже!");
                                            }
                                        }
                                    };
                                    r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                                }
                                else player.sendMessage("§cНекорректный ID!");
                            } else player.sendMessage("§cДанная команда работает во все дни, кроме воскресенья!");
                        }
                    }
                }
            }
        }

        // /farms transfer
        if (command.getName().equalsIgnoreCase("farms")) {
            if (strings.length == 1) {
                if (strings[0].equals("transfer")) {
                    Player player = (Player) commandSender;
                    int executingDayOfWeek = GetDate.getDayOfWeek();
                    if (executingDayOfWeek != 7) {
                        player.sendMessage("§6Укажите ID фермы, которую хотите передать и ник игрока, которому она передаётся. (Пример: /farms transfer AAA Zakviel)");
                    } else player.sendMessage("§cДанная команда работает во все дни, кроме воскресенья!");
                    return true;
                }
            }
        }

        // /farms transfer <id>
        if (command.getName().equalsIgnoreCase("farms")) {
            if (strings.length == 2) {
                if (strings[0].equals("transfer")) {
                    if (strings[1] != null) {
                        Player player = (Player) commandSender;
                        int executingDayOfWeek = GetDate.getDayOfWeek();
                        if (executingDayOfWeek != 7) {
                            player.sendMessage("§6Укажите ник игрока, которому передаётся ферма. (Пример: /farms transfer AAA Zakviel)");
                        } else player.sendMessage("§cДанная команда работает во все дни, кроме воскресенья!");
                        return true;
                    }
                }
            }
        }
        // /farms transfer <id> <player>.
        if (command.getName().equalsIgnoreCase("farms")) {
            if (strings.length == 3) {
                if (strings[0].equals("transfer")) {
                    if (strings[1] != null) {
                        if (strings[2] != null) {
                            if (commandSender instanceof Player) {
                                Player sender = (Player) commandSender;
                                int executingDayOfWeek = GetDate.getDayOfWeek();
                                    if(strings[1].matches("[A-Z]*")) {
                                    if (executingDayOfWeek != 7) {
                                        UUID target = Objects.requireNonNull(Bukkit.getOfflinePlayer(strings[2])).getUniqueId();
                                        if (Bukkit.getOfflinePlayer(target) != null && Bukkit.getOfflinePlayer(target).isOnline()) {
                                            Player targetPlayer = Bukkit.getPlayer(target);
                                            UUID senderUUID = sender.getUniqueId();
                                            BukkitRunnable r = new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        openConnection();
                                                        Statement statement = connection.createStatement();
                                                        ResultSet result = statement.executeQuery("SELECT * FROM farms WHERE Type=0 AND Owner='" + sender.getUniqueId().toString() + "' AND ID='" + strings[1] +"';");
                                                        if (result.next()) {
                                                            if(isInTransfer(strings[1]))
                                                                sender.sendMessage("§cОшибка: ферма уже находиться в ожидании передачи!");
                                                            else {
                                                                String world = result.getString("World");
                                                                int x = result.getInt("X");
                                                                int y = result.getInt("Y");
                                                                int z = result.getInt("Z");
                                                                TextComponent message = new TextComponent("§6Игрок §f" + sender.getName() + " §6желает передать Вам ферму §f" + result.getString("Name") + " (");
                                                                TextComponent coords = new TextComponent(x + " " + y + " " + z);
                                                                coords.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§fМир: §b" + world + "\n§fКликните, чтобы скопировать координаты в буфер обмена.")));
                                                                coords.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, x + " " + y + " " + z));
                                                                message.addExtra(coords);
                                                                message.addExtra(")\n§7Действия: ");
                                                                TextComponent accept = new TextComponent("§a§n[Принять]");
                                                                accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§fПринять запрос. Ферма автоматически добавиться в Ваш список, однако Вам потребуеться заплатить налог за неё уже на этой неделе.")));
                                                                accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/farms accept " + strings[1]));
                                                                message.addExtra(accept);
                                                                message.addExtra(" ");
                                                                TextComponent decline = new TextComponent("§c§n[Отклонить]");
                                                                decline.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§fОтклонить запрос.")));
                                                                decline.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/farms decline " + strings[1]));
                                                                message.addExtra(decline);
                                                                targetPlayer.spigot().sendMessage(message);
                                                                sender.sendMessage("§6Заявка на передачу фермы отправлена! Она будет активна в течении 30 секунд.");
                                                            }
                                                        }
                                                        else {
                                                            sender.sendMessage("§cОшибка: запись не найдена!");
                                                        }
                                                        connection.close();
                                                    } catch (ClassNotFoundException | SQLException e) {
                                                        e.printStackTrace();
                                                        sender.sendMessage("§cПроизошла ошибка, попробуйте позже!");
                                                    }
                                                }
                                            };
                                            r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                                            return true;
                                        }
                                        sender.sendMessage("§6Данный игрок оффлайн, пожалуйста, отправьте запрос, когда игрок будет онлайн.");
                                    } else sender.sendMessage("§cДанная команда работает во все дни, кроме воскресенья!");
                                }
                                else sender.sendMessage("§cНекорректный ID!");
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // /farms decline
        if (command.getName().equalsIgnoreCase("farms")) {
            if (strings.length == 1) {
                if (strings[0].equals("decline")) {
                    Player player = (Player) commandSender;
                    int executingDayOfWeek = GetDate.getDayOfWeek();
                    if (executingDayOfWeek != 7) {
                        player.sendMessage("§6Укажите ID фермы, запрос с которой Вы хотите отменить. (Пример: /farms decline AAA)");
                    } else player.sendMessage("§6Данная команда работает во все дни, кроме воскресенья!");
                    return true;
                }
            }
        }

        // /farms decline <id>
        if (command.getName().equalsIgnoreCase("farms")) {
            if (strings.length == 2) {
                if (strings[0].equals("decline")) {
                    if (strings[1] != null) {
                        Player player = (Player) commandSender;
                        int executingDayOfWeek = GetDate.getDayOfWeek();
                        if (executingDayOfWeek != 7) {
                            if(strings[1].matches("[A-Z]*")) {
                                if(isInTransfer(strings[1])){
                                    Triplet<Player, Player, Integer> data = transfer.get(strings[1]);
                                    data.a.sendMessage("§6Игрок §f" + data.b + " §6отклонил Вашу заявку на передачу фермы §f" + strings[1] + "§6!");
                                    transfer.remove(strings[1]);
                                    player.sendMessage("§6Вы отклонили запрос!");
                                }
                                else {
                                    player.sendMessage("§cЗаявка не найдена!");
                                }
                            }
                            else player.sendMessage("§cНекорректный ID!");
                        } else player.sendMessage("§cДанная команда работает во все дни, кроме воскресенья!");
                        return true;
                    }
                }
            }
        }

        // /farms accept <id>
        if (command.getName().equalsIgnoreCase("farms")) {
            if (strings.length == 2) {
                if (strings[0].equals("accept")) {
                    if (strings[1] != null) {
                        Player player = (Player) commandSender;
                        int executingDayOfWeek = GetDate.getDayOfWeek();
                        if (executingDayOfWeek != 7) {
                            if(strings[1].matches("[A-Z]*")) {
                                if(isInTransfer(strings[1])){
                                    Triplet<Player, Player, Integer> data = transfer.get(strings[1]);
                                    if(data.b.equals(player)) {
                                        BukkitRunnable r = new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    openConnection();
                                                    Statement statement = connection.createStatement();
                                                    ResultSet result = statement.executeQuery("SELECT * FROM farms WHERE Type=0 AND Owner='" + data.a.getUniqueId().toString() + "' AND ID='" + strings[1] + "';");
                                                    if (result.next()) {
                                                        String SQLString = "UPDATE farms SET Owner='" + player.getUniqueId().toString() + "' WHERE Type=0 AND Owner='" + data.a.getUniqueId().toString() + "' AND ID='" + strings[1] + "';";
                                                        statement.executeUpdate(SQLString);
                                                        data.a.sendMessage("§6Игрок §f" + data.b + " §6принял Вашу заявку на передачу фермы §f" + strings[1] + "§6!");
                                                        player.sendMessage("§6Вы приняли запрос!");
                                                        synchronized (transfer){
                                                            transfer.remove(strings[1]);
                                                        }
                                                        printFarmList(player);
                                                    } else {
                                                        player.sendMessage("§cОшибка: запись не найдена!");
                                                    }
                                                    connection.close();
                                                } catch (ClassNotFoundException | SQLException e) {
                                                    e.printStackTrace();
                                                    player.sendMessage("§cПроизошла ошибка, попробуйте позже!");
                                                }
                                            }
                                        };
                                        r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                                    }
                                    else player.sendMessage("§cЗаявка не найдена!");
                                }
                                else {
                                    player.sendMessage("§cЗаявка не найдена!");
                                }
                            }
                            else player.sendMessage("§cНекорректный ID!");
                        } else player.sendMessage("§cДанная команда работает во все дни, кроме воскресенья!");
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void printFarmList(Player player){
        int executingDayOfWeek = GetDate.getDayOfWeek();
        BaseComponent message = new TextComponent("====================================================\n");
        UUID targeterUUID = player.getUniqueId();
        message.addExtra("§6Список ваших ферм (налог учитывается):\n");
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    openConnection();
                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery("SELECT * FROM farms WHERE Type=0;");
                    boolean iterated = false;
                    while (result.next()) {
                        iterated = true;
                        UUID ownerUUID = UUID.fromString(result.getString("Owner"));
                        String name = result.getString("Name");
                        String id = result.getString("ID");
                        String world = result.getString("World");
                        int x = result.getInt("X");
                        int y = result.getInt("Y");
                        int z = result.getInt("Z");
                        if (ownerUUID.equals(targeterUUID)) {
                            TextComponent unit = new TextComponent("§f   [ID: " + id + "] §e" + name + " §f(");
                            TextComponent coords = new TextComponent(x + " " + y + " " + z);
                            coords.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§fМир: §b" + world + "\n§fКликните, чтобы скопировать координаты в буфер обмена.")));
                            coords.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, x + " " + y + " " + z));
                            unit.addExtra(coords);
                            unit.addExtra(")");
                            if(executingDayOfWeek != 7){
                                unit.addExtra("\n§7   Действия: ");
                                TextComponent delete = new TextComponent("§c§n[Удалить]");
                                delete.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§fСоздаст заявку на удаление фермы.\n§fОна будет обработана в близжайшее воскресенье.")));
                                delete.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/farms remove " + id));
                                unit.addExtra(delete);
                                unit.addExtra(" ");
                                TextComponent transfer = new TextComponent("§b§n[Передать]");
                                transfer.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§fОтправит запрос на передачу фермы игроку.\n§fЗапрос активен 30 секунд и при принятии ферма мгновенно перейдёт к игроку.")));
                                transfer.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/farms transfer " + id + " НикПолучателя"));
                                unit.addExtra(transfer);
                                unit.addExtra(" ");
                                TextComponent rename = new TextComponent("§a§n[Переименовать]");
                                rename.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§fПереименовывает ферму.\n§fНазвание меняется мгновенно.")));
                                rename.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/farms rename " + id + " " + name));
                                unit.addExtra(rename);
                            }
                            unit.addExtra("\n");
                            message.addExtra(unit);
                        }
                        ;
                    }
                    if(!iterated) message.addExtra("§e   У вас пока что нет ферм!\n");
                    ResultSet result2 = statement.executeQuery("SELECT * FROM farms WHERE Type=2 or Type=-2;");
                    boolean iterated2 = false;
                    while (result2.next()) {
                        if(!iterated2) message.addExtra("§6Заявки на удаление ферм (налог учитывается):\n");
                        iterated2 = true;
                        UUID ownerUUID = UUID.fromString(result2.getString("Owner"));
                        String name = result2.getString("Name");
                        String id = result2.getString("ID");
                        String world = result2.getString("World");
                        int x = result2.getInt("X");
                        int y = result2.getInt("Y");
                        int z = result2.getInt("Z");
                        if (ownerUUID.equals(targeterUUID)) {
                            TextComponent unit = new TextComponent("§f   [ID: " + id + "] §e" + name + " §f(");
                            TextComponent coords = new TextComponent(x + " " + y + " " + z);
                            coords.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§fМир: §b" + world + "\n§fКликните, чтобы скопировать координаты в буфер обмена.")));
                            coords.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, x + " " + y + " " + z));
                            unit.addExtra(coords);
                            unit.addExtra(")");
                            if(executingDayOfWeek != 7){
                                unit.addExtra("\n§7   Действия: ");
                                TextComponent delete = new TextComponent("§b§n[Отменить]");
                                delete.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§fОтменяет запрос.")));
                                delete.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/farms remove " + id + " decline"));
                                unit.addExtra(delete);
                            }
                            unit.addExtra("\n");
                            message.addExtra(unit);
                        }
                    }
                    ResultSet result3 = statement.executeQuery("SELECT * FROM farms WHERE Type=1 or Type=-1;");
                    boolean iterated3 = false;
                    while (result3.next()) {
                        if(!iterated3) message.addExtra("§6Заявки на добавление ферм (налог §nне§r§6 учитывается):\n");
                        iterated3 = true;
                        UUID ownerUUID = UUID.fromString(result3.getString("Owner"));
                        String name = result3.getString("Name");
                        String id = result3.getString("ID");
                        String world = result3.getString("World");
                        int x = result3.getInt("X");
                        int y = result3.getInt("Y");
                        int z = result3.getInt("Z");
                        if (ownerUUID.equals(targeterUUID)) {
                            TextComponent unit = new TextComponent("§f   [ID: " + id + "] §e" + name + " §f(");
                            TextComponent coords = new TextComponent(x + " " + y + " " + z);
                            coords.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§fМир: §b" + world + "\n§fКликните, чтобы скопировать координаты в буфер обмена.")));
                            coords.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, x + " " + y + " " + z));
                            unit.addExtra(coords);
                            unit.addExtra(")");
                            if(executingDayOfWeek != 7){
                                unit.addExtra("\n§7   Действия: ");
                                TextComponent delete = new TextComponent("§b§n[Отменить]");
                                delete.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§fОтменяет запрос.")));
                                delete.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/farms add " + id + " decline"));
                                unit.addExtra(delete);
                            }
                            unit.addExtra("\n");
                            message.addExtra(unit);
                        }
                    }
                    if(executingDayOfWeek != 7){
                        TextComponent add = new TextComponent("§a§n[Добавить ферму]");
                        add.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§fСоздать запрос на добавление фермы.\n§fОн будет обработан в близжайшее воскресение.\n§f§nДля добавления фермы вы должны находиться непосредственно на координатах самой фермы!")));
                        add.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/farms add Название Вашей фермы"));
                        message.addExtra(add);
                        message.addExtra("\n");
                    }
                    //message.addExtra("§fОбратите внимание, за сокрытие ферм вы можете быть наказаны модерацией.\n§fВ случае, если вы не пользуетесь какой-либо фермой, вы можете её удалить,\n§fно вам следует оставить табличку для модераторов, с сообщением об этом.");
                    message.addExtra("====================================================");
                    player.spigot().sendMessage(message);
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                    player.sendMessage("§cПроизошла ошибка, попробуйте позже!");
                }
            }
        };
        r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
    }

    // JDBC connector to MySQL database
    public void openConnection() throws SQLException, ClassNotFoundException {
        String host = SubTaxes.getPlugin(SubTaxes.class).host_server;
        int port = SubTaxes.getPlugin(SubTaxes.class).port_server;
        String database = SubTaxes.getPlugin(SubTaxes.class).database_server;
        String username = SubTaxes.getPlugin(SubTaxes.class).username_server;
        String password = SubTaxes.getPlugin(SubTaxes.class).password_server;

        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        }
    }
}
