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

    private void SetWorld(String worldName){
        _plugin.getConfig().set("world", worldName);
    }

    private int GetX(){
        return _plugin.getConfig().getInt("x");
    }
    private void SetX(int x){
        _plugin.getConfig().set("x", x);
    }

    private int GetY(){
        return _plugin.getConfig().getInt("y");
    }
    private void SetY(int y){
        _plugin.getConfig().set("y", y);
    }

    private int GetZ(){
        return _plugin.getConfig().getInt("z");
    }
    private void SetZ(int z){
        _plugin.getConfig().set("z", z);
    }

    public void SetSpawnLocation(Location location){
        World world = location.getWorld();
        if(world == null)
            return;

        SetWorld(world.getName());
        SetX(location.getBlockX());
        SetY(location.getBlockY());
        SetZ(location.getBlockZ());

        _plugin.saveConfig();
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