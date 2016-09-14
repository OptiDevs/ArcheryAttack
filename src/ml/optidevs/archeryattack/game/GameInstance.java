package ml.optidevs.archeryattack.game;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GameInstance {
	String gameID;
	String gameName;
	Location bounds1;
	Location bounds2;
	Location[] spawns;

	HashMap<Player, String> players;
	List<Player> playersList;
	HashMap<String, String>[] playerData;

	public GameInstance(String id, String mapName) {
		gameID = id;
	}
	
	public List<Player> getPlayers(){
		return playersList;
		
	}

	public String getID() {
		return gameID;
	}

	public String getName() {
		return gameName;
	}

	public void endGame() {

	}

	public void startGame() {
	}

	public void restartGame() {

	}

	public getPlayer getPlayer(Player plr){
		return new getPlayer(plr);
	}
	
	public class getPlayer {
		Player p;

		public getPlayer(Player plr) {
			p = plr;
		}

		public int getLives() {
			return Integer.valueOf(playerData[Integer.valueOf(players.get(p))].get("lives"));
		}
		
		public void removeLife(int i){
			int cl=Integer.valueOf(playerData[Integer.valueOf(players.get(p))].get("lives"));
			playerData[Integer.valueOf(players.get(p))].remove("lives");
			playerData[Integer.valueOf(players.get(p))].put("lives" , String.valueOf(cl-1));
		}
	}
}
