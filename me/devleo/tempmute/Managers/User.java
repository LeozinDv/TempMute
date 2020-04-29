package me.devleo.tempmute.Managers;

import me.devleo.tempmute.SQL.Dados;
import org.bukkit.entity.Player;

public class User {

    private final Player player;
    private final long time;

    public User(Player player, long time) {
        this.player = player;
        this.time = time;
    }

    public User(Player player) {
        this.player = player;
        this.time = Dados.get(player.getUniqueId().toString());
    }

    public Player getPlayer() {
        return player;
    }

    public long getTime() {
        return time;
    }
}
