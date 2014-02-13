package me.taemery0.PVM;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GameManager {
	public static void addPlayer(Player player, Integer arena) throws PVMException{
		if(!Vars.pluginSafe){
			throw new PVMException("plugin.notsafe");
		}
		if(Vars.playerGameStatus.containsKey(player.getName())){
			throw new PVMException("player.isingame");
		}
		try{
			player.teleport(Vars.locations(arena));
		} catch (PVMException e){
			throw new PVMException(e.getMessage());
		}
		Vars.clearInv(player);
		Vars.giveItems(player);
		Vars.healPlayer(player);
		Vars.playerGameStatus.put(player.getName(), arena);
		player.sendMessage(Vars.PVMPrefix + ChatColor.GREEN + "You Joined Arena " + arena);
	}
	public static void delPlayer(Player player) throws PVMException{
		if(!Vars.pluginSafe){
			throw new PVMException("plugin.notsafe");
		}
		if(!Vars.playerGameStatus.containsKey(player.getName())){
			throw new PVMException("player.isnotingame");
		}
		Vars.playerGameStatus.remove(player.getName());
		Vars.clearInv(player);
		Vars.healPlayer(player);
		try{
			player.teleport(Vars.locations(0));
		} catch (PVMException e){
			throw new PVMException(Vars.userError(e.getMessage()));
		}
		player.sendMessage(Vars.PVMPrefix + ChatColor.GREEN + "You Exited The Game");
	}
}
