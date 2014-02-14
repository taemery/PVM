package me.taemery0.PVM;

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
	public static void mobKill(Player player) {
		int current = killsobjective.getScore(player).getScore();
		killsobjective.getScore(player).setScore(current + 5);

	}
	public static void ingameScoreboard(Player player) {
		killsobjective.setDisplaySlot(DisplaySlot.SIDEBAR);
		killsobjective.setDisplayName(ChatColor.GOLD + "PVM Points:");
		player.setScoreboard(killsboard);
	}
	public static void resetPlayerPoints(Player player) {
		killsboard.resetScores(player);
	}
	public static void resetPlayerScoreboard(Player player){
		Scoreboard Scoreboard = manager.getNewScoreboard();
		player.setScoreboard(Scoreboard);
	}
}
