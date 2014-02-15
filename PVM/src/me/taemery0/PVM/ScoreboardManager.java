package me.taemery0.PVM;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {

	private static org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
	/*
	public static void statScoreboard(Player player) {
		Scoreboard stat = manager.getNewScoreboard();
		Objective main = stat.registerNewObjective("PVM-STATS", "dummy");
		main.setDisplayName(ChatColor.GOLD + "Welcome to PVM!");
		try {
			main.getScore(Bukkit.getOfflinePlayer("Points:")).setScore(
					mysql.getPoints(player));
		} catch (PVMException e) {
			main.getScore(Bukkit.getOfflinePlayer("Points:")).setScore(0);
			PlayerVsMob.logError(e.getMessage());
		}
		main.setDisplaySlot(DisplaySlot.SIDEBAR);
		player.setScoreboard(stat);
	}
	*/
	private static Scoreboard killsboard = manager.getNewScoreboard();
	public static Objective killsobjective = killsboard.registerNewObjective(
			ChatColor.GOLD + "PVM-Points", "dummy");
	public static Map<String, Integer> swordRank = new HashMap<String, Integer>();
	public static void mobKill(Player player) {
		int current = killsobjective.getScore(player).getScore();
		killsobjective.getScore(player).setScore(current + 5);
	}
	public static void ingameScoreboard(Player player) {
		killsobjective.setDisplaySlot(DisplaySlot.SIDEBAR);
		killsobjective.setDisplayName(ChatColor.GOLD + "PVM Points:");
		player.setScoreboard(killsboard);
		swordRank.put(player.getName(), 0);
	}
	public static void resetPlayerPoints(Player player) {
		killsboard.resetScores(player);
	}
	public static void resetPlayerScoreboard(Player player){
		Scoreboard Scoreboard = manager.getNewScoreboard();
		player.setScoreboard(Scoreboard);
		swordRank.remove(player.getName());
	}
	public static void setPlayerKillsPoints(Player player, Integer score){
		killsobjective.getScore(player).setScore(score);
	}
	public static Integer getPlayerKillsPoints(Player player){
		return killsobjective.getScore(player).getScore();
	}
}
