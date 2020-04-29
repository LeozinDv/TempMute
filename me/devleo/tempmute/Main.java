package me.devleo.tempmute;

import me.devleo.tempmute.Managers.Manager;
import me.devleo.tempmute.SQL.Dados;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main plugin;

    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        Dados.load();
        Bukkit.getPluginManager().registerEvents(new Events(), this);
        getCommand("tempmute").setExecutor(new Commands());
        getCommand("unmute").setExecutor(new Commands());
        getLogger().info("Enabled!");
    }

    public void onDisable() {
        Manager.saveAll();
    }
}
