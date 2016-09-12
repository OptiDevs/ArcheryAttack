package ml.optidevs.archeryattack.game;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class Mechanics implements Listener {

    public ArrayList<Projectile> arrows = new ArrayList<Projectile>();
	
	@EventHandler
	public void arrowShoot(EntityShootBowEvent e) {
		if (e.getEntity() instanceof Player) {
			e.getProjectile().setCustomName(e.getEntity().getUniqueId().toString());
			e.getProjectile().setCustomNameVisible(false);
			e.getEntity().sendMessage("Arrow shot!");
		} else {
			e.getProjectile().setCustomName("__");
			e.getProjectile().setCustomNameVisible(false);
		}

	}

	@EventHandler
	public void arrowEvent(ProjectileHitEvent event) {

		if (event.getEntity() instanceof Arrow) {

			Arrow arrow = (Arrow) event.getEntity();
			Entity shooter = (Entity) arrow.getShooter();

			if (shooter instanceof Player) {
				Player p = (Player) shooter;

				p.sendMessage("Arrow hit!");
				arrows.remove(event.getEntity());
			}
		}

	}

	@EventHandler
	public void arrowHitEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Arrow) {
			if (event.getEntity() instanceof Player) {
				Arrow arrow = (Arrow) event.getDamager();
				if (!(arrow.getCustomName().equals("__"))) {
					Player s = Bukkit.getPlayer(arrow.getCustomName());
					Player p = (Player) event.getEntity();
					s.sendMessage("Arrow hit " + p.getName() + "!");
					p.sendMessage("You were shot by " + s.getName() + "!");
				}

			}
		}

	}
}
