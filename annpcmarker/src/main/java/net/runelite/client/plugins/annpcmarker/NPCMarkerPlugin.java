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
package net.runelite.client.plugins.annpcmarker;

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
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.ui.overlay.OverlayManager;
import org.pf4j.Extension;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@Extension
@PluginDescriptor(
	name = "An NPC Marker",
	description = "Renders graphics over Non-Playable Characters.",
	tags = {"tag1", "tag2"},
	type = PluginType.UTILITY
)
@Slf4j
public class NPCMarkerPlugin extends Plugin {

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private NPCMarkerConfig config;

	@Inject
	private NPCMarkerOverlay overlay;

	@Provides
	NPCMarkerConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(NPCMarkerConfig.class);
	}

	@Override
	protected void startUp() {
		overlayManager.add(overlay);
		sortConfigNPCNames();
	}

	@Override
	protected void shutDown() {
		overlayManager.remove(overlay);
	}

	@Getter(AccessLevel.PACKAGE)
	List<NPC> worldNPCS = new ArrayList<>();
	@Getter(AccessLevel.PACKAGE)
	HashMap<String, Color> configNPCDetails = new HashMap<>();

	@Subscribe
	private void on(NpcSpawned event)
	{
		NPC npc = event.getNpc();
		if (npc == null)
		{
			return;
		}
		worldNPCS.add(npc);
	}

	@Subscribe
	private void on(NpcDespawned event)
	{
		NPC npc = event.getNpc();
		if (npc == null)
		{
			return;
		}
		worldNPCS.remove(npc);
	}

	@Subscribe
	private void on(ConfigChanged event)
	{
		configNPCDetails.clear();
	}

	private void sortConfigNPCNames()
	{
		if (config.configNPCNames().isEmpty())
		{
			System.out.println("The config text field is empty.");
			return;
		}
		String[] parts = splitString(removeCharacters(config.configNPCNames()), ",");
		String configName = null;
		Color configColor = null;
		System.out.println("-------------------");
		for (String s : parts)
		{
			if (!s.contains(":"))
			{
				// The string does not contain :, therefore there is no specific color.
				configName = s;
				configColor = config.defaultNPCHighlightColor();
			}
			if (s.contains(":"))
			{
				// The string contains :, therefore the string needs to be split again to gain other details.
				String[] npcDetails = splitString(s, ":");
				if (npcDetails.length >= 1)
				{
					configName = npcDetails[0];
				}
				if (npcDetails.length >= 2)
				{
					try
					{
						configColor = Color.decode("#" + npcDetails[1]);
					}
					catch (NumberFormatException nfe)
					{
						configColor = config.defaultNPCHighlightColor();
						System.out.println("Error decoding color.");
					}
				}
			}
			System.out.println("NPC Name: " + configName + "\nColor: " + configColor);
			System.out.println("-------------------");
			configNPCDetails.put(configName, configColor);
		}
	}

	private String removeCharacters(String string)
	{
		string = string.toLowerCase();
		string = string.replaceAll(" ", "");
		string = string.replaceAll("\n", "");
		return string;
	}

	private String[] splitString(String string, String splitChar)
	{
		return string.split(Pattern.quote(splitChar));
	}
}
