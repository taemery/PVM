package me.taemery0.PVM;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Vars {
	private static final Plugin plugin = PVM.plugin;
	public static boolean pluginSafe = false;
	public static String PVMPrefix = ChatColor.BLUE + "[PVM] "
			+ ChatColor.RESET;
	public static String PVMUsage = ChatColor.BLUE + "[PVM] " + ChatColor.RED
			+ "Correct Usage: /PVM join [arena] [quit] [leave]";
	public static String PVMMustBePlayer = ChatColor.BLUE + "[PVM] "
			+ ChatColor.RED + "You must be a player to run that command!";

	public static String userError(String string) {
		if (string == "player.isingame") {
			return "§cYou are currently already in a game!";
		}
		if (string == "player.isnotingame") {
			return "§cYou currently aren't in a game!";
		}
		if (string == "arena.doesnotexist") {
			return "§cThat arena doesn't exist";
		}
		if (string == "plugin.notsafe") {
			return "§cPlugin is not safe state";
		}
		if (string == "arena.notactive") {
			return "§cThat arena isn't active";
		}
		return "§An Error Occured";
	}

	public static Map<String, Integer> playerGameStatus = new HashMap<String, Integer>();

	public static Location locations(int arena) throws PVMException {
		if(arena != 0){
			double x = plugin.getConfig().getDouble("Arenas." + arena + ".x", -0);
			double y = plugin.getConfig().getDouble("Arenas." + arena + ".y", -0);
			double z = plugin.getConfig().getDouble("Arenas." + arena + ".z", -0);
			World w = Bukkit.getWorld(plugin.getConfig().getString(
					"Arenas." + arena + ".w", "world"));
			boolean active = plugin.getConfig().getBoolean(
					"Arenas." + arena + ".active", false);
			if (x == -0 && y == -0 && z == -0) {
				throw new PVMException("arena.doesnotexist");
			}
			if (!active) {
				throw new PVMException("arena.notactive");
			}
			return new Location(w, x, y, z);
		}else{
			double x = plugin.getConfig().getDouble("Lobby.x", -0);
			double y = plugin.getConfig().getDouble("Lobby.y", -0);
			double z = plugin.getConfig().getDouble("Lobby.z", -0);
			World w = Bukkit.getWorld(plugin.getConfig().getString("Lobby.w"));
			if (x == -0 && y == -0 && z == -0){
				throw new PVMException("arena.doesntexist");
			}
			return new Location(w, x, y, z);
		}
	}

	public static void giveItems(Player player) throws PVMException {
		for (String key : plugin.getConfig()
				.getConfigurationSection("GivenItems").getKeys(false)) {
			player.getInventory().setItem(player.getInventory().firstEmpty(), 
					new ItemStack(Material.getMaterial(plugin.getConfig()
							.getString("GivenItems." + key))));
		}
		player.getInventory().setHelmet(new ItemStack(Material.getMaterial(plugin.getConfig().getString("Armor.Helmet", "AIR"))));
		player.getInventory().setChestplate(new ItemStack(Material.getMaterial(plugin.getConfig().getString("Armor.Chestplate", "AIR"))));
		player.getInventory().setLeggings(new ItemStack(Material.getMaterial(plugin.getConfig().getString("Armor.Leggings", "AIR"))));
		player.getInventory().setBoots(new ItemStack(Material.getMaterial(plugin.getConfig().getString("Armor.Boots", "AIR"))));
	}
	public static void clearInv(Player player){
		player.getInventory().clear();
		player.getInventory().setBoots(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
	}
	public static void healPlayer(Player player){
		player.setHealth(20.0);
		player.setFoodLevel(20);
		player.setFireTicks(0);
	}
}
