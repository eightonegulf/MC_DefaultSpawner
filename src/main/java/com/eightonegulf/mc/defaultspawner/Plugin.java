package com.eightonegulf.mc.defaultspawner;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

public class Plugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(this, this);
    }
    @Override
    public void onDisable() {
        getLogger().info("Disabled");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location newLocation = player.getLocation().getWorld().getBlockAt(0,83,0).getLocation();
        event.getPlayer().teleport(newLocation);
    }
}