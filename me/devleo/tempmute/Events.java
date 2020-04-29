package me.devleo.tempmute;

import me.devleo.tempmute.Managers.Manager;
import me.devleo.tempmute.SQL.Dados;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {

    @EventHandler
    private void join(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
            if (Dados.contains(e.getPlayer().getUniqueId().toString())) {
                if (Dados.get(e.getPlayer().getUniqueId().toString()) < System.currentTimeMillis()) {
                    Dados.delete(e.getPlayer().getUniqueId().toString());
                    return;
                }
                Manager.insert(e.getPlayer());
            }
        });
    }

    @EventHandler
    private void quit(PlayerQuitEvent e) {
        if (Manager.contains(e.getPlayer()))
            Manager.save(e.getPlayer());
    }

    @EventHandler
    private void say(AsyncPlayerChatEvent e) {
        if (Manager.contains(e.getPlayer()))
            if (System.currentTimeMillis() < Manager.get(e.getPlayer()).getTime()) {
                e.setCancelled(true);
                e.getPlayer().sendMessage("§cYou are temporarily silenced!");
            } else {
                Dados.delete(e.getPlayer().getUniqueId().toString());
            }
    }
}

