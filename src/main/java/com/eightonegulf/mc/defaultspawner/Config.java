package com.eightonegulf.mc.defaultspawner;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

class Config {
    private static JavaPlugin _plugin;
    public Config(JavaPlugin plugin){
        _plugin = plugin;
        _plugin.saveDefaultConfig();
    }

    private String GetWorld(){
        return _plugin.getConfig().getString("world");
    }
    private int GetX(){
        return _plugin.getConfig().getInt("x");
    }
    private int GetY(){
        return _plugin.getConfig().getInt("y");
    }
    private int GetZ(){
        return _plugin.getConfig().getInt("z");
    }

    public Location GetSpawnLocation(){
        String worldName = GetWorld();

        if(worldName == null) {
            return null;
        }

        World world = _plugin.getServer().getWorld(worldName);
        if(world == null){
            _plugin.getLogger().warning("Unknown world " + worldName);
            return null;
        }

        int coordinateX = GetX();
        int coordinateY = GetY();
        int coordinateZ = GetZ();
        return world.getBlockAt(coordinateX, coordinateY, coordinateZ).getLocation();
    }
}