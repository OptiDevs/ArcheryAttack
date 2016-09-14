package ml.optidevs.archeryattack.general;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import ml.optidevs.archeryattack.DataStore;

public class Events implements Listener {
	public void onLogin(PlayerLoginEvent e) {

	}

	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage(null);
		DataStore.playerAdd(p);
		DataStore.playerJoinGame(p, "test");
	}
}
