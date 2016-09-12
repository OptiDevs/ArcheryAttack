package ml.optidevs.archeryattack;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	

	public ConsoleCommandSender Logger = getServer().getConsoleSender();
	public PluginDescriptionFile desc = getDescription();
	public static YamlConfiguration LANG;
	public static File LANG_FILE;
	
	@Override
	public void onEnable() {
		loadConfig();
		Logger.sendMessage("[ArcheryAttack] Plugin Enabled " + desc.getVersion());
	}
	
	@Override
	public void onDisable() {
		Logger.sendMessage("[ArcheryAttack] Plugin Disabled " + desc.getVersion());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage("WIP");
		return true;
	}
	
	public void loadConfig() {
		getConfig().getDefaults();
		saveDefaultConfig();
		reloadConfig();
		Logger.sendMessage("[OptiDevs] Configuation Loaded");
	}
	
	public void loadLang() {
		File lang = new File(getDataFolder(), "lang.yml");
		if (!lang.exists()) {
			try {
				getDataFolder().mkdir();
				lang.createNewFile();
				InputStream defConfigStream = this.getResource("lang.yml");
				if (defConfigStream != null) {
					YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(LANG_FILE);
					defConfig.save(lang);
					Lang.setFile(defConfig);
					return;
				}
			} catch (IOException e) {
				Logger.sendMessage("ERROR [OptiChat] Couldn't create language file.");
				Logger.sendMessage("ERROR [OptiChat] This is a fatal error. Now disabling");
			}
		}
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
		for (Lang item : Lang.values()) {
			if (conf.getString(item.getPath()) == null) {
				conf.set(item.getPath(), item.getDefault());
			}
		}
		Lang.setFile(conf);
		Main.LANG = conf;
		Main.LANG_FILE = lang;
		try {
			conf.save(getLangFile());
		} catch (IOException e) {
			e.printStackTrace();
			Logger.sendMessage("ERROR [OptiChat] Failed to save lang.yml.");
			Logger.sendMessage("ERROR [OptiChat] Report this stack trace to OptiDevs.");
		}
	}
	
	public YamlConfiguration getLang() {
		return LANG;
	}

	public File getLangFile() {
		return LANG_FILE;
	}
}
