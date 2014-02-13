package me.taemery0.PVM;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PVMEventHandler implements Listener {
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		try {
			GameManager.delPlayer(event.getEntity());
			event.getDrops().clear();
			event.setDroppedExp(0);
			event.setDeathMessage(ChatColor.BLUE
					+ "[PVM] "
					+ ChatColor.RED
					+ event.getEntity().getName()
					+ " was killed by "
					+ WordUtils.capitalizeFully(getKiller(event).getType()
							.name()));
		} catch (PVMException e) {
			if (e.getMessage() != "player.isnotingame") {
				event.getEntity().sendMessage(
						Vars.PVMPrefix + ChatColor.RED + e.getMessage());
			}
		}
	}

	public Entity getKiller(EntityDeathEvent event) {
		EntityDamageEvent entityDamageEvent = event.getEntity()
				.getLastDamageCause();
		if ((entityDamageEvent != null) && !entityDamageEvent.isCancelled()
				&& (entityDamageEvent instanceof EntityDamageByEntityEvent)) {
			Entity damager = ((EntityDamageByEntityEvent) entityDamageEvent)
					.getDamager();
			if (damager instanceof Projectile) {
				@SuppressWarnings("deprecation")
				LivingEntity shooter = ((Projectile) damager).getShooter();
				if (shooter != null)
					return shooter;
			}
			return damager;
		}
		return null;
	}

	@EventHandler
	public void noWaterEntry(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Location loc = player.getLocation();
		if (loc.getBlock().getType() == Material.WATER
				|| loc.getBlock().getType() == Material.STATIONARY_WATER) {
			try {
				if (Vars.playerGameStatus.containsKey(player.getName())) {
					player.teleport(Vars.locations(Vars.playerGameStatus
							.get(event.getPlayer().getName())));
					player.sendMessage(Vars.PVMPrefix + "" + ChatColor.GOLD
							+ "You can't go into water!");
				}
			} catch (PVMException e) {
			}
		}
	}

	@EventHandler
	public void filterDamage(EntityDamageEvent event) {
		if (event.getCause() == EntityDamageEvent.DamageCause.FALL
				|| event.getCause() == EntityDamageEvent.DamageCause.FIRE
				|| event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		event.setCancelled(true);
	}

	public void onEntityDeath(EntityDeathEvent event) {
		event.getDrops().clear();
		event.setDroppedExp(0);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (Vars.playerGameStatus.containsKey(event.getPlayer())) {
			try {
				GameManager.delPlayer(event.getPlayer());
			} catch (PVMException e) {
				//
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (PVM.plugin.getConfig().getBoolean("Lobby.teleport-on-join")) {
			try {
				event.getPlayer().teleport(Vars.locations(0));
			} catch (PVMException e) {
			}
		}
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (Vars.playerGameStatus.containsKey(event.getPlayer().getName())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (Vars.playerGameStatus.containsKey(event.getPlayer().getName())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if (e.getLine(0).equalsIgnoreCase("[PVM]")) {
			e.setLine(0, ChatColor.BLUE + "[PVM]");
			String arena = e.getLine(1);
			try {
				Vars.locations(Integer.parseInt(arena));
			} catch (PVMException | NumberFormatException ex) {
				e.getPlayer().sendMessage(
						Vars.PVMPrefix + "" + ChatColor.RED
								+ Vars.userError(ex.getMessage()));
				return;
			}
			e.setLine(1, ChatColor.GOLD + arena);
		}
	}

	@EventHandler
	public void SignClick(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if (sign.getLine(0).equalsIgnoreCase(ChatColor.BLUE + "[PVM]")) {
					try {
						player.sendMessage(sign.getLine(1));
						GameManager.addPlayer(player,
								Integer.parseInt(sign.getLine(1).substring(2)));
					} catch (PVMException ex) {
						player.sendMessage(Vars.PVMPrefix + "" + ChatColor.RED
								+ Vars.userError(ex.getMessage()));
					}
				}
			}
		}
	}
}
