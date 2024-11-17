package com.eightonegulf.mc.defaultspawner;

import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.generator.WorldInfo;

import java.util.List;
import java.util.stream.Collectors;

public class Command implements CommandExecutor, TabCompleter {
    private final Plugin _plugin;
    private final Config _config;

    public Command(Plugin plugin, Config config){
        _plugin = plugin;
        _config = config;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        if(args.length == 0)
            return false;

        if(args[0].equals("show")){
            Location spawnLocation = _config.GetSpawnLocation();
            if(spawnLocation == null){
                commandSender.sendMessage("No valid spawn has been set");
                return true;
            }

            commandSender.sendMessage("Current spawn location: " + LocationToString(spawnLocation));
            return true;
        }

        if(args[0].equals("set")){
            if(args.length < 2)
                return false;

            if(args[1].equals("current")){
                if (!(commandSender instanceof Player player)){
                    commandSender.sendMessage("Only players can issue this command");
                    return true;
                }

                setCoordinate(commandSender, player.getLocation());
                return true;
            }
            if(args[1].equals("coordinate")){
                return onCommandSetCoordinate(commandSender, args);
            }
        }

        return false;
    }

    private void setCoordinate(CommandSender commandSender, Location location){
        _config.SetSpawnLocation(location);
        commandSender.sendMessage("New spawn location: " + LocationToString(location));
    }

    private String LocationToString(Location location){
        return location.getWorld().getName() + " " +
                location.getX() + " " +
                location.getY() + " " +
                location.getZ();
    }

    private boolean onCommandSetCoordinate(CommandSender commandSender, String[] args){
        if(args.length < 6)
            return false;

        org.bukkit.Server server = _plugin.getServer();

        String worldName = args[2];
        try{
            double x = Double.parseDouble(args[3]);
            double y = Double.parseDouble(args[4]);
            double z = Double.parseDouble(args[5]);

            if(server.getWorlds().stream().noneMatch(t -> t.getName().equals(worldName))){
                commandSender.sendMessage("Unknown world " + worldName);
                return false;
            }

            Location newLocation = new Location(server.getWorld(worldName), x,y,z);
            setCoordinate(commandSender, newLocation);
            return true;

        } catch (NumberFormatException e) {
            commandSender.sendMessage("Invalid coordinate format");
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        if(args.length <= 1)
            return List.of("show", "set");

        if(args[0].equals("show")){
            return List.of();
        }
        if(args[0].equals("set")){
            return onTabCompleteSet(args);
        }

        return List.of();
    }

    private List<String> onTabCompleteSet(String[] args){
        if(args.length == 2){
            return List.of("current", "coordinate");
        }

        if(args[1].equals("current")){
            return List.of("");
        }
        if(args[1].equals("coordinate")){
            return onTabCompleteCoordinate(args);
        }

        return List.of();
    }

    private List<String> onTabCompleteCoordinate(String[] args){
        if(args.length == 3){
            return _plugin.getServer().getWorlds().stream().map(WorldInfo::getName).collect(Collectors.toList());
        }

        if(args.length == 4){
            return List.of("[X]");
        }
        if(args.length == 5){
            return List.of("[Y]");
        }
        if(args.length == 6){
            return List.of("[Z]");
        }
        return List.of();
    }
}
