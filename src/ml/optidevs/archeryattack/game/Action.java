package ml.optidevs.archeryattack.game;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Action {

	public static void onPlayerJoinGame(GameInstance gi, Player p) {
		p.sendMessage("Joined game ");
	}

	public static Scoreboard getSB(GameInstance gi) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard pb = manager.getNewScoreboard();
		Objective objective = pb.registerNewObjective("dummy", "title");
		objective.setDisplayName("§bPlayers");
		List<Player> players = gi.getPlayers();
		for (Player p : players) {
			if (gi.getPlayer(p).getLives() <= 1) {
				Score score = objective.getScore("§c" + p.getName());
				score.setScore(gi.getPlayer(p).getLives());
			} else {
				Score score = objective.getScore("§a" + p.getName());
				score.setScore(gi.getPlayer(p).getLives());
			}
		}

		return pb;
	}

}
