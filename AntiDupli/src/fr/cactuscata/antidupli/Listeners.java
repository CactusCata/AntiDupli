package fr.cactuscata.antidupli;

import java.util.List;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Listeners implements Listener {

	public static boolean soon = false;
	private final String warnMessage = "§cVous n'avez pas la permission de dupliquer !";
	
	@EventHandler
	public final void onJoin(PlayerJoinEvent event){
		System.out.println(event.getPlayer().getLocation());
	}

	@EventHandler
	private final void onDropItem(final PlayerDropItemEvent event) {
		check(event.getItemDrop(), event.getPlayer(), event, false);
	}

	@EventHandler
	private final void pickupItem(final PlayerPickupItemEvent event) {
		check(event.getItem(), event.getPlayer(), event, true);
	}

	@EventHandler
	private final void clickInventory(final InventoryClickEvent event) {

		if (event.getClickedInventory() == null || event.getClickedInventory().getHolder() == null)
			return;

		for (final ItemStack item : new ItemStack[] { event.getCurrentItem(), event.getCursor() }) {
			if (isDuplicate(item)) {
				event.getWhoClicked().getInventory().remove(item);
				event.getClickedInventory().remove(item);
				event.getInventory().remove(item);
				event.getWhoClicked().sendMessage(warnMessage);
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public final void command(PlayerCommandPreprocessEvent event){
		event.getPlayer().closeInventory();
	}

	@EventHandler
	public final void openInv(final InventoryOpenEvent event) {
		if (soon)
			event.setCancelled(true);

		for (ItemStack items : event.getInventory().getContents())
			if (items != null)
				System.out.println(items.getType());

	}

	private final boolean isDuplicate(final ItemStack item) {
		if (item != null) {
			final ItemMeta itemM = item.getItemMeta();
			if (itemM != null && itemM.getLore() != null) {
				final List<String> lore = itemM.getLore();
				if (lore.contains("§6§lInfos de vente :") || lore.contains("§d§lAptitude") || lore.contains("§b§l[HDV]")
						|| lore.get(lore.size() - 1).endsWith("*") || lore.get(lore.size() - 1).startsWith("&3Prix:")
						|| (itemM.getDisplayName() != null && itemM.getDisplayName().equals("§b§lFondre")))
					return true;
			}
		}
		return false;
	}

	private final void check(final Item item, final Player player, final Event event, final boolean b) {
		if (isDuplicate(item.getItemStack())) {
			item.remove();
			player.sendMessage(warnMessage);
			((Cancellable) event).setCancelled(b);
		}
	}

}
