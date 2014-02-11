package me.taemery0.PVM;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GameManager {
	public static void addPlayer(Player player, Integer arena) throws PVMException{
		/* Check If Plugin Is Safe To Run */
		if(!Vars.pluginSafe){
			throw new PVMException("plugin.notsafe");
		}
		/* Check if player is not in game */
		if(Vars.playerGameStatus.containsKey(player.getName())){
			throw new PVMException("player.isingame");
		}
		try{
			player.teleport(Vars.arenas(arena));
		} catch (PVMException e){
			throw new PVMException(e.getMessage());
		}
		Vars.playerGameStatus.put(player.getName(), arena);
		player.sendMessage(Vars.PVMPrefix + ChatColor.GREEN + "You Joined Arena " + arena);
	}
	public static void delPlayer(Player player) throws PVMException{
		/* Check If Plugin Is Safe To Run */
		if(!Vars.pluginSafe){
			throw new PVMException("plugin.notsafe");
		}
		/* Check if player is in game */
		if(!Vars.playerGameStatus.containsKey(player.getName())){
			throw new PVMException("player.isnotingame");
		}
		Vars.playerGameStatus.remove(player.getName());
		player.sendMessage(Vars.PVMPrefix + ChatColor.GREEN + "You Exited The Game");
	}
}
