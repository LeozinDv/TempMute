package me.devleo.tempmute;

import me.devleo.tempmute.Managers.Manager;
import me.devleo.tempmute.Managers.User;
import me.devleo.tempmute.SQL.Dados;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tempmute")) {
            if (!sender.hasPermission("tempmute.use")) {
                sender.sendMessage("§cYou are not allowed to do this.");
                return true;
            }
            if (args.length != 2) {
                sender.sendMessage("§cIncorrect syntax, use: /tempmute <player> <temp>");
                sender.sendMessage("§cTime in minutes!");
                return true;
            }
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage("§cPlayer not found, try again.");
                return true;
            }
            if (Manager.contains(player)) {
                sender.sendMessage("§cThe player '" + player.getName() + "' is already silenced.");
                return true;
            }
            try {
                int time = Integer.parseInt(args[1]);
                if (time < 1) {
                    sender.sendMessage("§cInvalid number format, try something greater than 0.");
                    return true;
                }
                long final_time = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(time);
                Manager.set(player, new User(player, final_time));
                sender.sendMessage("§aThe player was successfully silenced.");
            } catch (NumberFormatException ignored) {
                sender.sendMessage("§cInvalid number format, try something greater than 0.");
            }
        }
        if (cmd.getName().equalsIgnoreCase("unmute")) {
            if (!sender.hasPermission("unmute.use")) {
                sender.sendMessage("§cYou are not allowed to do this.");
                return true;
            }
            if (args.length != 1) {
                sender.sendMessage("§cIncorrect syntax, use: /unmute <player>");
                return true;
            }
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage("§cPlayer not found, try again.");
                return true;
            }
            if (!Manager.contains(player)) {
                sender.sendMessage("§cThe player '" + player.getName() + "' is not muted.");
                return true;
            }
            Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    Dados.delete(player.getUniqueId().toString());
                    Manager.remove(player);
                    sender.sendMessage("§aThe player is no longer mutated.");
                }
            });
        }
        return false;
    }
}
