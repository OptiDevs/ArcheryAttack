package ml.optidevs.archeryattack;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class Signs implements Listener {

	Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ArcheryAttack");

	@EventHandler
	public void onCreate(SignChangeEvent e) {
		String[] ln = e.getLines();
		// Player p = e.getPlayer();

		if (ln[0].equalsIgnoreCase("[Archery]")) {
			e.setLine(0, ChatColor.DARK_BLUE + "[Archery]");
			if (ln[1].isEmpty()) {
				e.setLine(0, ChatColor.RED + "[Archery]");
				e.setLine(1, ChatColor.RED + "Error");
				e.setLine(2, ChatColor.RED + "");
				e.setLine(3, ChatColor.RED + "");
				return;
			}
			e.setLine(1, ChatColor.GREEN + ln[1]);
			e.setLine(2, ChatColor.translateAlternateColorCodes('&', ln[2]));
			e.setLine(3, ChatColor.translateAlternateColorCodes('&', ln[3]));
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (e.getClickedBlock().equals(Material.SIGN_POST) || e.getClickedBlock().equals(Material.WALL_SIGN)) {

				Sign s = (Sign) e.getClickedBlock().getState();

				String[] ln = s.getLines();
				if (ChatColor.stripColor(ln[1]).equalsIgnoreCase("[Archery]")) {
				}
			}
		}
	}
}
