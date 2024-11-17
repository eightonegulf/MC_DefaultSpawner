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

    private double GetX(){
        return _plugin.getConfig().getInt("x");
    }
    private void SetX(double x){
        _plugin.getConfig().set("x", x);
    }

    private double GetY(){
        return _plugin.getConfig().getDouble("y");
    }
    private void SetY(double y){
        _plugin.getConfig().set("y", y);
    }

    private double GetZ(){
        return _plugin.getConfig().getDouble("z");
    }
    private void SetZ(double z){
        _plugin.getConfig().set("z", z);
    }

    public void SetSpawnLocation(Location location){
        World world = location.getWorld();
        if(world == null)
            return;

        SetWorld(world.getName());
        SetX(location.getX());
        SetY(location.getY());
        SetZ(location.getZ());

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

        double coordinateX = GetX();
        double coordinateY = GetY();
        double coordinateZ = GetZ();

        return new Location(world, coordinateX, coordinateY, coordinateZ);
    }
}