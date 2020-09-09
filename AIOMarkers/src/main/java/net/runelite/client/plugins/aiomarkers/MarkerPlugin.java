/*
 * Copyright (c) 2018, James Swindle <wilingua@gmail.com>
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
package net.runelite.client.plugins.aiomarkers;

import com.google.inject.Provides;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;

import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.plugins.nmutils.Utils;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import java.util.ArrayList;
import java.util.List;

@Extension
@PluginDescriptor(
	name = "AIO Markers",
	description = "An AIO Marker plugin for Player's, NPC's, Game Objects, Inventory Items and Ground Items.",
	tags = {"tag1", "tag2"},
	type = PluginType.UTILITY
)
@Slf4j
@SuppressWarnings("unused")
@PluginDependency(Utils.class)
public class MarkerPlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private MarkerConfig config;

	@Inject
	private MarkerOverlay overlay;

	@Inject
	private Utils util;

	@Provides
	MarkerConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(MarkerConfig.class);
	}

	@Override
	protected void startUp() {
		overlayManager.add(overlay);
		getConfigTextField();
		getAllNPCS();
	}

	@Override
	protected void shutDown() {
		overlayManager.remove(overlay);
		configName.clear();
	}

	@Getter(AccessLevel.PACKAGE)
	List<NPC> worldNPC = new ArrayList<>();
	@Getter(AccessLevel.PACKAGE)
	List<String> configName = new ArrayList<>();
	@Getter(AccessLevel.PACKAGE)
	List<Integer> configId = new ArrayList<>();

	@Subscribe
	private void on(NpcSpawned event)
	{
		NPC npc = event.getNpc();
		if (npc == null)
		{
			return;
		}
		compareNPC(npc);
	}

	@Subscribe
	private void on(NpcDespawned event)
	{
		NPC npc = event.getNpc();
		if (npc == null)
		{
			return;
		}
		worldNPC.remove(npc);
	}

	@Subscribe
	private void on(ConfigChanged event)
	{
		if (!event.getGroup().equals("marker"))
		{
			return;
		}
		if (config.highlightNPC())
		{
			worldNPC.clear();
			configName.clear();
			configId.clear();
			getConfigTextField();
			getAllNPCS();
		}
	}

	private void compareNPC(NPC npc)
	{
		if (npc == null)
		{
			return;
		}
		String npcName = npc.getName();
		if (npcName == null)
		{
			return;
		}
		npcName = npcName.toLowerCase().replace(" ", "");
		for (String s : configName) {
			if (npcName.contains(s))
			{
				if (!worldNPC.contains(npc))
				{
					worldNPC.add(npc);
				}
			}
		}
		int npcId = npc.getId();
		for (Integer i : configId)
		{
			if (npcId == i)
			{
				if (!worldNPC.contains(npc))
				{
					worldNPC.add(npc);
				}
			}
		}
	}

	private void getConfigTextField()
	{
		if (config.configNPCTextField() == null || config.configNPCTextField().isEmpty())
		{
			return;
		}
		String[] names = config.configNPCTextField().toLowerCase().replace(" ", "").split(",");
		for (String string : names)
		{
			if (string == null)
			{
				return;
			}
			if (string.matches(".*\\d.*"))
			{
				getConfigNPCId(string);
			}
			else
			{
				getConfigName(string);
			}
		}
		System.out.println("Config NPC Names: " + getConfigName());
		System.out.println("Config NPC IDs: " + getConfigId());
	}

	private void getConfigName(String string)
	{
 		if (string == null)
		{
			return;
		}
		string = string.toLowerCase();
 		string = string.replaceAll(" ", "");
 		if (!configName.contains(string))
		{
			configName.add(string);
		}
	}

	private void getConfigNPCId(String string)
	{
		if (string == null)
		{
			return;
		}
		string = string.toLowerCase();
		string = string.replaceAll(" ", "");
		string = string.replaceAll("\\D+", "");
		if (!configName.contains(string))
		{
			configId.add(Integer.parseInt(string));
		}
	}

	private void getAllNPCS()
	{
		List<NPC> npcs = client.getNpcs();
		for (NPC npc : npcs)
		{
			if (npc == null)
			{
				return;
			}
			compareNPC(npc);
		}
	}

	public boolean hasLineOfSight(Player player, NPC npc)
	{
		if (player == null)
		{
			return false;
		}
		if (npc == null)
		{
			return false;
		}
		return player.getWorldArea().hasLineOfSightTo(client, npc.getWorldArea());
	}
}
