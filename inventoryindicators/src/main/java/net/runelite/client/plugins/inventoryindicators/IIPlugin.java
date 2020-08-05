/*
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.inventoryindicators;

import javax.inject.Inject;

import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.input.MouseManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

@Extension
@PluginDescriptor(
	name = "Inventory Indicators",
	description = "Display indicators based on various inventory related states.",
	tags = {"ahk", "inventory", "nomore"},
	type = PluginType.UTILITY
)
@Slf4j
public class IIPlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private IIConfig config;

	@Inject
	private MouseManager mouseManager;

	@Inject
	private InventoryOverlay inventoryOverlay;

	@Inject
	private SceneOverlay sceneOverlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ItemManager itemManager;

	@Provides
	IIConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(IIConfig.class);
	}

	@Override
	protected void startUp() {
		overlayManager.add(sceneOverlay);
		overlayManager.add(inventoryOverlay);
	}

	@Override
	protected void shutDown() {
		overlayManager.remove(sceneOverlay);
		overlayManager.remove(inventoryOverlay);
	}

	@Getter(AccessLevel.PACKAGE)
	boolean inventoryFull = false;
	boolean inventoryContains = false;
	private HashMap<String, Integer> inventoryItem = new HashMap<>();
	private HashMap<String, Integer> indicatorOptions = new HashMap<>();

	@Subscribe
	private void on(ItemContainerChanged event) {
		ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
		if (inventory == null)
		{
			return;
		}
		Item[] items = inventory.getItems();
		if (config.displayFull())
		{
			int i = 0;
			for (Item item : items) {
				if (item == null || item.getId() == -1)
				{
					continue;
				}
				i++;
			}
			inventoryFull = i == 28;
		}
		if (config.displayContain())
		{
			// Get the full config name string.
			String fullConfigString = config.containName().toLowerCase();
			// Check if the full config name string is not null/
			if (fullConfigString.isEmpty())
			{
				inventoryContains = false;
				System.out.println("fullConfigString is empty.");
				return;
			}
			// Replace the spaces with nothing in the full config name string.
			fullConfigString = fullConfigString.replaceAll(" ", "");
			fullConfigString = fullConfigString.replaceAll("\n", "");
			// Split the full config name string on every , eg.. 1 - name:amount, 2 - name:amount,
			String[] splitFullConfigString = fullConfigString.split(Pattern.quote(","));
			// Check if the split string is null (might not be needed but fuck it.)
			for (String singleString : splitFullConfigString)
			{
				// Check if the single string is null.
				if (singleString.isEmpty())
				{
					inventoryContains = false;
					System.out.println("splitFullConfigString is empty.");
					return;
				}
				// Split the name and number from each other.
				if (!singleString.contains(":"))
				{
					System.out.println("Item name: \"" + singleString + "\", and item amount: \"1\"");
					inventoryItem.put(singleString, 1);
				}
				else
				{
					String[] string = singleString.split(Pattern.quote(":"));
					if (string.length == 1)
					{
						System.out.println("Item name: \"" + singleString + "\", and item amount: \"1\"");
						inventoryItem.put(singleString, 1);
					}
					else
					{
						String itemName = string[0];
						String removedTextString = string[1].replaceAll("[^0-9]","");
						int itemAmount = Integer.parseInt(removedTextString);
						System.out.println("Item name: \"" + itemName + "\", and item amount: \"" + itemAmount + "\"");
						inventoryItem.put(itemName, itemAmount);
					}
				}
			}

			for (Item item : items)
			{
				if (item == null)
				{
					return;
				}
				for(Map.Entry<String, Integer> entry : inventoryItem.entrySet()) {
					String configName = entry.getKey();
					Integer configAmount = entry.getValue();
					String inventoryItemName = itemManager.getItemDefinition(item.getId()).getName().toLowerCase();
					inventoryItemName = inventoryItemName.replaceAll(" ","");
					int inventoryItemAmount = item.getQuantity();
					if (inventoryItemName.contains(configName) && inventoryItemAmount >= configAmount)
					{
						System.out.println("Inventory item match: " + inventoryItemName);
						inventoryContains = true;
						inventoryItem.clear();
						return;
					}
				}
				inventoryContains = false;
			}
		}
	}
}