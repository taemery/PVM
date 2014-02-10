package me.taemery0.PVM;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class PVM extends JavaPlugin{
	
	public static PVM plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	
	public void onEnable() {
		plugin = this;
		getCommand("pvm").setExecutor(new PVMCommandHandler());
		getServer().getPluginManager().registerEvents(new EventHandler(), this);
		this.saveDefaultConfig();
	}
	public void onDisable() {
		
	}
	
}
