package ml.optidevs.archeryattack.game;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.spigotmc.event.entity.EntityDismountEvent;

import ml.optidevs.archeryattack.Main;

public class Mechanics implements Listener {

	public ArrayList<Projectile> arrows = new ArrayList<Projectile>();

	@EventHandler
	public void arrowShoot(EntityShootBowEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			ItemMeta bow = e.getBow().getItemMeta();
			e.getProjectile().setCustomName(e.getEntity().getName());
			String bowType = "none";
			if (bow.getLore().get(0).contains("Heal")) {
				bowType = "heal";
			} else {
				bowType = "normal";
			}
			e.getProjectile().setCustomNameVisible(true);
			e.getProjectile().setMetadata("BOWTYPE", new FixedMetadataValue(Main.gi(), bowType));

		} else {
			e.getProjectile().setCustomName("__");
			e.getProjectile().setCustomNameVisible(true);
		}

	}

	@EventHandler
	public void arrowEvent(ProjectileHitEvent event) {
		if (!(event.getEntity() instanceof Arrow))
			return;

		Arrow arrow = (Arrow) event.getEntity();
		Player p = (Player) arrow.getShooter();
		World world = arrow.getWorld();
		BlockIterator bi = new BlockIterator(world, arrow.getLocation().toVector(), arrow.getVelocity().normalize(), 0,
				4);
		Block hit = null;

		while (bi.hasNext()) {
			hit = bi.next();
			if (hit.getType() != Material.AIR) {
				break;
			}
		}
		if (hit.getType().equals(Material.CHEST)) {
			p.sendMessage("You got a treasure!");
			hit.setType(Material.AIR);
			event.getEntity().remove();
			int x = hit.getLocation().getBlockX();
			int y = hit.getLocation().getBlockY();
			int z = hit.getLocation().getBlockZ();
			Location loc = new Location(arrow.getWorld(), x, y, z);
			Firework fw = (Firework) arrow.getWorld().spawn(loc, Firework.class);
			FireworkMeta fm = fw.getFireworkMeta();
			fm.addEffect(FireworkEffect.builder().flicker(false).trail(false).with(Type.BALL)
					.withColor(Color.OLIVE, Color.YELLOW).withFade(Color.SILVER).build());
			fm.setPower(0);
			fw.setFireworkMeta(fm);
			p.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
		}

	}

	public void arrowHitBlock() {

	}

	@EventHandler
	public void arrowHitEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Arrow) {
			if (event.getEntity() instanceof ArmorStand) {
				event.setCancelled(true);
				return;
			}
			if (event.getEntity() instanceof Player) {
				Arrow arrow = (Arrow) event.getDamager();
				if (!(arrow.getCustomName().equals("__"))) {
					// Player s = Bukkit.getPlayer(arrow.getCustomName());
					Player p = (Player) event.getEntity();

					event.setCancelled(true);
					// Bow types
					String bowtype = arrow.getMetadata("BOWTYPE").get(0).asString();
					if (bowtype == "normal") {
						p.damage(20);
						shootFireWork(p.getLocation(), Type.STAR, Color.BLACK, Color.RED, Color.MAROON);
					} else if (bowtype == "heal") {
						p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 60, 4, true), true);
						p.setMaxHealth(20);
						p.setHealth(p.getMaxHealth());
						p.damage(-1);
						event.setCancelled(true);
						p.sendMessage(ChatColor.AQUA + "You were healed by " + ChatColor.LIGHT_PURPLE
								+ arrow.getCustomName() + ChatColor.AQUA + "!");
						shootFireWork(p.getLocation(), Type.BURST, Color.WHITE, Color.FUCHSIA, Color.AQUA, Color.WHITE);
					}
					arrow.remove();
				}

			}
		}

	}

	@EventHandler
	public void playerKilled(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		e.setDroppedExp(0);
	}

	@EventHandler
	public void batOff(EntityDismountEvent e) {
		if (e.getDismounted() instanceof Arrow) {
			if (e.getDismounted().getCustomName() != "__") {
				Bat b = (Bat) e.getEntity();
				b.remove();
				b.setHealth(0);
			}
		}

	}

	public void shootFireWork(Location loc, Type type, Color fade, Color... colors) {
		Firework fw = (Firework) loc.getWorld().spawn(loc, Firework.class);
		FireworkMeta fm = fw.getFireworkMeta();
		fm.addEffect(FireworkEffect.builder().flicker(false).trail(false).with(type).withColor(colors).withFade(fade)
				.build());
		fm.setPower(0);
		fw.setFireworkMeta(fm);
	}
}
