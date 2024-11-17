package com.eightonegulf.mc.defaultspawner;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

public class Plugin extends JavaPlugin implements Listener {
    Config _config;

    @Override
    public void onEnable() {
        _config = new Config(this);

        getServer().getPluginManager().registerEvents(this, this);
        getCommand("defaultspawner").setExecutor(new Command(this, _config));
    }
    @Override
    public void onDisable() {
        _config = null;
        getLogger().info("Disabled");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Location newLocation = _config.GetSpawnLocation();
        if(newLocation == null)
            return;

        event.getPlayer().teleport(newLocation);
    }
}