package me.taemery0.PVM;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PVM extends JavaPlugin{
	
	public static PVM plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	
	public void onEnable() {
		plugin = this;
		getCommand("pvm").setExecutor(new PVMCommandHandler());
		getServer().getPluginManager().registerEvents(new PVMEventHandler(), this);
		this.saveDefaultConfig();
		if(plugin.getConfig().getBoolean("HasBeenSetup")){
			Vars.pluginSafe = true;
		}else{
			logger.log(Level.WARNING, "[PVM] The Plugin Config Has Not Been Setup!");
			logger.log(Level.WARNING, "[PVM] The Plugin Will Not Run Unless It Has Been Setup");
		}
	}
	public void onDisable() {
		if(Bukkit.getOnlinePlayers().length != 0){
			for (String key : Vars.playerGameStatus.keySet()) {
					try {
						GameManager.delPlayer(Bukkit.getPlayer(key));
					} catch (PVMException e) {
						logger.log(Level.WARNING, "[PVM] " + e.getMessage());
					}	
			}
		}
	}
}
