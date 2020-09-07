/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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
package net.runelite.client.plugins.marker;

import net.runelite.client.config.*;

import java.awt.Color;

@ConfigGroup("marker")
public interface MarkerConfig extends Config
{
	@ConfigTitleSection(
			keyName = "configurationOptions",
			name = "Configuration Options",
			description = "",
			position = 1
	)
	default Title firstTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "markerConfiguration",
			name = "Show options for",
			description = "",
			position = 2,
			titleSection = "configurationOptions"
	)
	default MarkerConfiguration markerConfiguration() { return MarkerConfiguration.NPC; }

	@ConfigItem(
			keyName = "highlightNPC",
			name = "Enable NPC Highlight",
			description = "",
			position = 3,
			titleSection = "configurationOptions",
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC"
	)
	default boolean highlightNPC() { return false; }

	@ConfigItem(
			keyName = "configNPCTextField",
			name = "Highlight NPC with name / id.",
			description = "Use , to split up NPC names and IDs.",
			position = 4,
			titleSection = "configurationOptions",
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC"
	)
	default String configNPCTextField() { return "Banker, 3010"; }

	@ConfigItem(
			keyName = "npcHighlightColor",
			name = "Default Color",
			description = "NPC default highlighting color.",
			position = 5,
			titleSection = "configurationOptions",
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC"
	)
	default Color npcHighlightColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "npcInteractingWithPlayerColor",
			name = "Player Interaction",
			description = "The color of the overlay if the NPC is interacting with the Player.",
			position = 6,
			titleSection = "configurationOptions",
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC"
	)
	default Color npcInteractingWithPlayerColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "npcLineOfSight",
			name = "Only Show NPC's you can \"see\"",
			description = "",
			position = 7,
			titleSection = "configurationOptions",
			hidden = true,
			unhide = "markerConfiguration",
			unhideValue = "NPC"
	)
	default boolean npcLineOfSight() { return false; }

}