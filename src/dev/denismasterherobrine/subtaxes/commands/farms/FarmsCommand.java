package dev.denismasterherobrine.subtaxes.commands.farms;

import dev.denismasterherobrine.subtaxes.SubTaxes;
import dev.denismasterherobrine.subtaxes.cycles.GetDate;
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

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
            if (command.getName().equalsIgnoreCase("farms")){
                if (commandSender instanceof Player) {
                        if (strings.length == 0) {
                            commandSender.sendMessage("Command usage: /farms add/remove <name of the farm>");
                            commandSender.sendMessage("Command usage: /farms transfer <name of the farm> <player nickname to transfer the farm>");
                            commandSender.sendMessage("Command usage: /farms decline to decline ALL FARMS. /farms decline <name of the farm> to decline a specific farm from transferring.");
                            return false;
                        }
                    }

                // /farms remove
                if (strings.length == 1){
                    if (strings[0].equals("remove")){
                        Player player = (Player) commandSender;
                        int executingDayOfWeek = GetDate.getDayOfWeek();
                        System.out.println(executingDayOfWeek);
                        if(executingDayOfWeek != 7) {
                            player.sendMessage("Укажите ферму, которую хотите удалить! (Пример: /farms remove IronFarm)");
                            return true;
                        }
                        else player.sendMessage("Данная команда работает во все дни, кроме воскресенья!");
                    }
                }
                // /farms add
                if (strings.length == 1){
                    if (strings[0].equals("add")){
                        if (commandSender instanceof Player){
                            Player player = (Player) commandSender;
                            int executingDayOfWeek = GetDate.getDayOfWeek();
                            System.out.println(executingDayOfWeek);
                            if(executingDayOfWeek != 7) {
                                String world = player.getWorld().getName();
                                Location loc = player.getLocation();
                                double x = loc.getX();
                                double y = loc.getY();
                                double z = loc.getZ();
                                UUID PlayerUUID = player.getUniqueId();
                                player.sendMessage("Ваша ферма успешно добавлена в список на рассмотрение!");
                                System.out.println("Player Add Info: " + world + " " + x + " " + y + " " + z + ".");
                                BukkitRunnable r = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            openConnection();
                                            Statement statement = connection.createStatement();
                                            String SQLString = "INSERT INTO FarmsTableTest (type, PlayerUUID, world, x, y, z, name) VALUES (0, '" + PlayerUUID + "', '" + world + "', " + x + ", " + y + ", " + z + ", '<no name is provided>');";
                                            statement.executeUpdate(SQLString);
                                            connection.close();
                                        } catch (ClassNotFoundException | SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                                return true;
                            }
                            else player.sendMessage("Данная команда работает во все дни, кроме воскресенья!");
                    }
                }
            }
            }
            // /farms add <name>
            if (command.getName().equalsIgnoreCase("farms")){
                if (strings.length == 2){
                    if (strings[0].equals("add")){
                        if (strings[1] != null){
                            Player player = ((Player) commandSender);
                            int executingDayOfWeek = GetDate.getDayOfWeek();
                            System.out.println(executingDayOfWeek);
                            if(executingDayOfWeek != 7) {
                                String world = player.getWorld().getName();
                                Location loc = player.getLocation();
                                double x = loc.getX();
                                double y = loc.getY();
                                double z = loc.getZ();
                                UUID PlayerUUID = player.getUniqueId();
                                player.sendMessage("Ваша ферма " + strings[1] + " успешно добавлена в список на рассмотрение!");
                                System.out.println("Player Add Info: " + world + " " + x + " " + y + " " + z + ".");
                                BukkitRunnable r = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            openConnection();
                                            Statement statement = connection.createStatement();
                                            String SQLString = "INSERT INTO FarmsTableTest (type, PlayerUUID, world, x, y, z, name) VALUES (0, '" + PlayerUUID + "', '" + world + "', " + x + ", " + y + ", " + z + ", '" + strings[1] + "');";
                                            statement.executeUpdate(SQLString);
                                            connection.close();
                                        } catch (ClassNotFoundException | SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                                return true;
                            }
                            else player.sendMessage("Данная команда работает во все дни, кроме воскресенья!");
                        }
                    }
                }
            }
            // /farms remove <name>
            if (command.getName().equalsIgnoreCase("farms")){
                if (strings.length == 2){
                    if (strings[0].equals("remove")){
                        if (strings[1] != null){
                            Player player = ((Player) commandSender);
                            int executingDayOfWeek = GetDate.getDayOfWeek();
                            System.out.println(executingDayOfWeek);
                            if(executingDayOfWeek != 7) {
                                String world = player.getWorld().getName();
                                Location loc = player.getLocation();
                                double x = loc.getX();
                                double y = loc.getY();
                                double z = loc.getZ();
                                UUID PlayerUUID = player.getUniqueId();
                                player.sendMessage("Ваша ферма " + strings[1] + " успешно добавлена в список на удаление!");
                                System.out.println("Player Remove Info: " + world + " " + x + " " + y + " " + z + ".");
                                BukkitRunnable r = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            openConnection();
                                            Statement statement = connection.createStatement();
                                            String SQLString = "INSERT INTO FarmsTableTest (type, PlayerUUID, world, x, y, z, name) VALUES (1, '" + PlayerUUID + "', '" + world + "', " + x + ", " + y + ", " + z + ", '" + strings[1] + "');";
                                            statement.executeUpdate(SQLString);
                                            connection.close();
                                        } catch (ClassNotFoundException | SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                                return true;
                            }
                            else player.sendMessage("Данная команда работает во все дни, кроме воскресенья!");
                    }
                }
            }
        }
            // /farms transfer
        if (strings.length == 1){
            if (strings[0].equals("transfer")){
                Player player = (Player) commandSender;
                int executingDayOfWeek = GetDate.getDayOfWeek();
                System.out.println(executingDayOfWeek);
                if(executingDayOfWeek != 7) {
                    player.sendMessage("Укажите ферму, которую хотите передать и ник игрока, которому передаётся она. (Пример: /farms transfer IronFarm Zakviel)");
                }
                else player.sendMessage("Данная команда работает во все дни, кроме воскресенья!");
                return true;
            }
        }

        // /farms transfer <name>
        if (strings.length == 2) {
            if (strings[0].equals("transfer")) {
                if (strings[1] != null) {
                    Player player = (Player) commandSender;
                    int executingDayOfWeek = GetDate.getDayOfWeek();
                    System.out.println(executingDayOfWeek);
                    if(executingDayOfWeek != 7) {
                        player.sendMessage("Укажите ник игрока, которому передаётся ферма. (Пример: /farms transfer IronFarm Zakviel)");
                    }
                    else player.sendMessage("Данная команда работает во все дни, кроме воскресенья!");
                    return true;
                }
            }
        }

        // /farms transfer <name> <player>.
        if (strings.length == 3) {
            if (strings[0].equals("transfer")) {
                if (strings[1] != null) {
                    if (strings[2] != null)
                        if (commandSender instanceof Player) {
                        Player sender = (Player) commandSender;
                            int executingDayOfWeek = GetDate.getDayOfWeek();
                            System.out.println(executingDayOfWeek);
                            if(executingDayOfWeek != 7) {
                                UUID target = Objects.requireNonNull(Bukkit.getOfflinePlayer(strings[2])).getUniqueId();
                                if (Bukkit.getOfflinePlayer(target) != null && Bukkit.getOfflinePlayer(target).isOnline()) {
                                    Player targetPlayer = Bukkit.getPlayer(target);
                                    targetPlayer.sendMessage("Вам пришёл запрос на перенос фермы" + strings[1] + " от " + sender + ". Пропишите /farms decline <name>, чтобы отклонить.");
                                    sender.sendMessage("Отправлена заявка на передачу фермы!");
                                    UUID senderUUID = sender.getUniqueId();
                                    BukkitRunnable r = new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                openConnection();
                                                Statement statement = connection.createStatement();
                                                String SQLString = "INSERT INTO TransferFarmsTableTest (senderName, receiverName, name) VALUES ('" + senderUUID + "', '" + target + "', " + strings[1] + "');";
                                                statement.executeUpdate(SQLString);
                                                connection.close();
                                            } catch (ClassNotFoundException | SQLException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                                    return true;
                                }
                                sender.sendMessage("Данный игрок оффлайн, пожалуйста, отправьте запрос, когда игрок будет онлайн.");
                            }
                            else sender.sendMessage("Данная команда работает во все дни, кроме воскресенья!");
                            return false;
                }
            }
        }
    }

        // /farms decline
        if (strings.length == 1) {
            if (strings[0].equals("decline")) {
                Player player = (Player) commandSender;
                int executingDayOfWeek = GetDate.getDayOfWeek();
                System.out.println(executingDayOfWeek);
                if(executingDayOfWeek != 7) {
                    UUID targeterUUID = player.getUniqueId();
                    BukkitRunnable r = new BukkitRunnable() {
                        @Override
                        public void run() {
                            try {
                                openConnection();
                                Statement statement = connection.createStatement();
                                ResultSet result = statement.executeQuery("SELECT * FROM TransferFarmsTableTest;");
                                while (result.next()) {
                                    UUID senderUUID = UUID.fromString(result.getString("senderName"));
                                    UUID receiverUUID = UUID.fromString(result.getString("receiverName"));
                                    String name = result.getString("name");
                                    if (receiverUUID.equals(targeterUUID)){
                                        if (Bukkit.getOfflinePlayer(senderUUID) != null && Bukkit.getOfflinePlayer(senderUUID).isOnline()) {
                                            Player sender = Bukkit.getPlayer(senderUUID);
                                            sender.sendMessage("Ваши запрос был отклонён на ферму + " + name + ".");
                                        }
                                        player.sendMessage("Вы отклонили все запросы на передачу фермы.");
                                        Statement statement1 = connection.createStatement();
                                        String SQLString = "DELETE FROM TransferFarmsTableTest WHERE receiverName='" + receiverUUID + "';";
                                        statement1.executeUpdate(SQLString);
                                    };
                                }
                                connection.close();
                            } catch (ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                    player.sendMessage("Вам не приходило никаких запросов на передачу ферм, если ничего кроме этой надписи не написано!");
                }
                else player.sendMessage("Данная команда работает во все дни, кроме воскресенья!");
                return false;
                }
            }

        // /farms decline <name>
        if (strings.length == 2) {
            if (strings[0].equals("decline")) {
                if (strings[1] != null) {
                    Player player = (Player) commandSender;
                    int executingDayOfWeek = GetDate.getDayOfWeek();
                    System.out.println(executingDayOfWeek);
                    if(executingDayOfWeek != 7) {
                        UUID targeterUUID = player.getUniqueId();
                        BukkitRunnable r = new BukkitRunnable() {
                            @Override
                            public void run() {
                                try {
                                    openConnection();
                                    Statement statement = connection.createStatement();
                                    ResultSet result = statement.executeQuery("SELECT * FROM TransferFarmsTableTest;");
                                    while (result.next()) {
                                        UUID senderUUID = UUID.fromString(result.getString("senderName"));
                                        UUID receiverUUID = UUID.fromString(result.getString("receiverName"));
                                        String name = result.getString("name");
                                        if (receiverUUID.equals(targeterUUID) && name.equals(strings[1])) {
                                            if (Bukkit.getOfflinePlayer(senderUUID) != null && Bukkit.getOfflinePlayer(senderUUID).isOnline()) {
                                                Player sender = Bukkit.getPlayer(senderUUID);
                                                sender.sendMessage("Ваши запрос был отклонён на ферму + " + name + ".");
                                            }
                                            player.sendMessage("Вы отклонили запросы на передачу фермы " + name + ".");
                                            Statement statement1 = connection.createStatement();
                                            String SQLString = "DELETE FROM TransferFarmsTableTest WHERE receiverName = '" + receiverUUID + "' AND `name`='" + name + "';";
                                            statement1.executeUpdate(SQLString);
                                        }
                                        ;
                                    }
                                    connection.close();
                                } catch (ClassNotFoundException | SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                        player.sendMessage("Вам не приходило никаких запросов на передачу ферм, если ничего кроме этой надписи не написано!");
                    }
                    else player.sendMessage("Данная команда работает во все дни, кроме воскресенья!");
                    return true;
                }
            }
        }

        // /farms list
        if (strings.length == 1) {
            if (strings[0].equals("list")) {
                Player player = (Player) commandSender;
                    UUID targeterUUID = player.getUniqueId();
                    player.sendMessage("Список ваших ферм:");
                    BukkitRunnable r = new BukkitRunnable() {
                        @Override
                        public void run() {
                            try {
                                openConnection();
                                Statement statement = connection.createStatement();
                                ResultSet result = statement.executeQuery("SELECT * FROM FarmsListTable;");
                                while (result.next()) {
                                    UUID ownerUUID = UUID.fromString(result.getString("ownerName"));
                                    String name = result.getString("name");
                                    if (ownerUUID.equals(targeterUUID)) {
                                        player.sendMessage("У вас во владнении имеется ферма с названием: " + name + ".");
                                    }
                                    ;
                                }
                                connection.close();
                            } catch (ClassNotFoundException | SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    r.runTaskAsynchronously(JavaPlugin.getPlugin(SubTaxes.class));
                return false;
            }
        }
        return false;
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
