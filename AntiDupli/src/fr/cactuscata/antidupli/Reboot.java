package fr.cactuscata.antidupli;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Reboot implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Listeners.soon = true;
		sender.sendMessage("�cLes joueurs ne peuvent plus int�ragir avec les inventaires");
		return true;
	}

}
