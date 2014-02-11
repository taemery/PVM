package me.taemery0.PVM;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PVMCommandHandler implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = null;
		if(!(sender instanceof Player)){
			sender.sendMessage(Vars.PVMMustBePlayer);
			return true;
		}else{
			player = (Player) sender;
		}
		if(args.length == 0){
			player.sendMessage(Vars.PVMUsage);
			return true;
		}
		if(args.length == 1){
			if(args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("quit")){
				try {
					GameManager.delPlayer(player);
				} catch (PVMException e) {
					player.sendMessage(Vars.PVMPrefix + "" + ChatColor.RED + Vars.userError(e.getMessage()));
				}
			}else{
				player.sendMessage(Vars.PVMUsage);
			}
		}
		if(args.length == 2){
			Integer arena = null;
			try{
				arena = Integer.parseInt(args[1]);
			} catch (NumberFormatException e){
				player.sendMessage(Vars.PVMPrefix + "" + ChatColor.RED + "Arena Must Be A Number!");
				return false;
			}
			if(args[0].equalsIgnoreCase("join")){
				try {
					GameManager.addPlayer(player, arena);
				} catch (PVMException e) {
					player.sendMessage(Vars.PVMPrefix + Vars.userError(e.getMessage()));
				}
			}
		}
		return false;
	}
}
