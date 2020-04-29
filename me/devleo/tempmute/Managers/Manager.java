package me.devleo.tempmute.Managers;

import me.devleo.tempmute.SQL.Dados;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Manager {

    private static HashMap<Player, User> muteds = new HashMap<>();

    public static void set(Player player, User profile) {
        muteds.put(player, profile);
    }

    public static void insert(Player player) {
        muteds.put(player, new User(player));
    }

    public static User get(Player player) {
        return muteds.get(player);
    }

    public static boolean contains(Player key) {
        return muteds.containsKey(key);
    }

    public static void remove(Player key) {
        muteds.remove(key);
    }

    public static void save(Player player) {
        Dados.set(player.getUniqueId().toString(), muteds.get(player).getTime());
        muteds.remove(player);
    }

    public static void saveAll() {
        muteds.values().forEach(result -> Dados.set(result.getPlayer().getUniqueId().toString(), result.getTime()));
        muteds.clear();
    }
}
