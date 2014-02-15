package me.taemery0.PVM;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameManager {
	public static void addPlayer(Player player, Integer arena)
			throws PVMException {
		if (!Vars.pluginSafe) {
			throw new PVMException("plugin.notsafe");
		}
		if (Vars.playerGameStatus.containsKey(player.getName())) {
			throw new PVMException("player.isingame");
		}
		try {
			player.teleport(Vars.locations(arena));
		} catch (PVMException e) {
			throw new PVMException(e.getMessage());
		}
		Vars.clearInv(player);
		Vars.giveItems(player);
		Vars.healPlayer(player);
		ScoreboardManager.ingameScoreboard(player);
		Vars.playerGameStatus.put(player.getName(), arena);
		player.sendMessage(Vars.PVMPrefix + ChatColor.GREEN
				+ "You Joined Arena " + arena);
	}

	public static void delPlayer(Player player) throws PVMException {
		if (!Vars.pluginSafe) {
			throw new PVMException("plugin.notsafe");
		}
		if (!Vars.playerGameStatus.containsKey(player.getName())) {
			throw new PVMException("player.isnotingame");
		}
		Vars.playerGameStatus.remove(player.getName());
		Vars.clearInv(player);
		Vars.healPlayer(player);
		ScoreboardManager.resetPlayerPoints(player);
		ScoreboardManager.resetPlayerScoreboard(player);
		try {
			player.teleport(Vars.locations(0));
		} catch (PVMException e) {
			throw new PVMException(Vars.userError(e.getMessage()));
		}
		player.sendMessage(Vars.PVMPrefix + ChatColor.GREEN
				+ "You Exited The Game");
	}

	public static void upgradeSwordRank(Player player) {
		if (!Vars.playerSwordRank.containsKey(player.getName())) {
			Vars.playerSwordRank.put(player.getName(), 0);
		}
		Integer currentRank = Vars.playerSwordRank.get(player.getName());
		Integer wantedRank = currentRank + 1;
		Integer wantedRankCost;
		ItemStack wantedRankItem;
		try {
			wantedRankItem = Vars.getSwordForSwordRank(wantedRank);
		} catch (PVMException e) {
			player.sendMessage(Vars.PVMPrefix + ChatColor.RED
					+ Vars.userError(e.getMessage()));
			return;
		}
		try {
			wantedRankCost = Vars.getCostForSwordRank(wantedRank);
		} catch (PVMException e) {
			player.sendMessage(Vars.PVMPrefix + ChatColor.RED
				+ Vars.userError(e.getMessage()));
			return;
		}
		Integer playerPoints = ScoreboardManager.getPlayerKillsPoints(player);
		if(playerPoints < wantedRankCost){
			player.sendMessage(Vars.PVMPrefix + ChatColor.RED + "You don't have enough points! " + wantedRankCost + " Required");
		}else{
			player.getInventory().addItem(wantedRankItem);
			player.sendMessage(Vars.PVMPrefix + ChatColor.GOLD + "You upgraded your sword!");
			ScoreboardManager.setPlayerKillsPoints(player, playerPoints - wantedRankCost);
			Vars.playerSwordRank.put(player.getName(), wantedRank);
		}
	}
}
