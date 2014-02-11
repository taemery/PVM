package me.taemery0.PVM;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class Vars {
	private static final Plugin plugin = PVM.plugin;
	public static boolean pluginSafe = false;
	public static String PVMPrefix = ChatColor.BLUE + "[PVM] " + ChatColor.RESET;
	public static String PVMUsage = ChatColor.BLUE + "[PVM] " + ChatColor.RED + "Correct Usage: /PVM join [arena] [quit] [leave]";
	public static String PVMMustBePlayer = ChatColor.BLUE + "[PVM] " + ChatColor.RED + "You must be a player to run that command!";
	public static String userError(String string){
		if(string == "player.isingame"){
			return "§cYou are currently already in a game!";
		}
		if(string == "player.isnotingame"){
			return "§cYou currently aren't in a game!";
		}
		if(string == "arena.doesnotexist"){
			return "§cThat arena doesn't exist";
		}
		if(string == "plugin.notsafe"){
			return "§cPlugin is not safe state";
		}
		if(string == "arena.notactive"){
			return "§cThat arena isn't active";
		}
		return "§An Error Occured";
	}
	
	public static Map<String, Integer> playerGameStatus = new HashMap<String, Integer>();
	
	public static Location arenas(int arena) throws PVMException{
		double x = plugin.getConfig().getDouble("arenas." + arena + ".x", -0);
		double y = plugin.getConfig().getDouble("arenas." + arena + ".y", -0);
		double z = plugin.getConfig().getDouble("arenas." + arena + ".z", -0);
		World w = Bukkit.getWorld(plugin.getConfig().getString("arenas." + arena + ".w","world"));
		boolean active = plugin.getConfig().getBoolean("arenas." + arena + ".active", false);
		if(x==-0 && y==-0 && z==-0){
			throw new PVMException("arena.doesnotexist");
		}
		if(!active){
			throw new PVMException("arena.notactive");
		}
		return new Location(w,x,y,z);
	}
}
