package ml.optidevs.archeryattack;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import ml.optidevs.archeryattack.game.Action;
import ml.optidevs.archeryattack.game.GameInstance;

public abstract class DataStore {
	public static List<Player> players;
	public static HashMap<Player, String> PlayersInGame;
	public static List<Player> playersInQue;
	public static HashMap<String, GameInstance> games;
	public static List<GameInstance> allgames;

	public static List<GameInstance> getGames() {
		return allgames;

	}

	public static GameInstance newGame(String map){
		GameInstance gi = new GameInstance(String.valueOf(allgames.size()), map);
	
		return gi;
	}
	
	public static void playerAdd(Player p) {
		players.add(p);
	}

	public static void playerRemove(Player p) {
		players.remove(p);
	}

	public static List<Player> getPlayers() {
		return players;
	}

	public static boolean playerJoinGame(Player p, String gameID) {
		if (playerInGame(p))
			return false;
		PlayersInGame.put(p, gameID);
		Action.onPlayerJoinGame(DataStore.getGame(gameID), p);
		return true;
	}

	public static boolean playerInGame(Player p) {
		if (PlayersInGame.containsKey(p))
			return true;
		return false;
	}

	public static void playerLeaveGame(Player p, String gameID) {
		PlayersInGame.remove(p, PlayersInGame.get(p));
	}

	public static GameInstance getGameOfPlayer(Player p) {
		return getGame(PlayersInGame.get(p));
	}

	public static void playerAddQue(Player p) {
		playersInQue.add(p);
	}

	public static void playerRemoveQue(Player p) {
		playersInQue.remove(p);
	}

	public static boolean playerInQue(Player p) {
		for (Player s : playersInQue) {
			if (s.getUniqueId() == p.getUniqueId()) {
				return true;
			}
		}
		return false;
	}

	public static GameInstance getGame(String id) {
		return games.get(id);
	}

}
