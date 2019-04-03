package fr.cactuscata.antidupli;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class AntiDupli extends JavaPlugin implements Listener {

	public void onEnable() {
		getServer().getPluginManager().registerEvents(new Listeners(), this);
		getCommand("reboot").setExecutor(new Reboot());
	}

}
