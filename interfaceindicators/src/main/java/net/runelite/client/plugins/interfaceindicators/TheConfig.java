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
package net.runelite.client.plugins.interfaceindicators;

import net.runelite.client.config.*;

import java.awt.Color;


@ConfigGroup("aplugintutorial")
public interface TheConfig extends Config
{
	@ConfigTitleSection(
			keyName = "firstTitle",
			name = "First Title",
			description = "",
			position = 1
	)
	default Title firstTitle()
	{
		return new Title();
	}

	@ConfigItem(
			keyName = "configType",
			name = "Configuration Options",
			description = "",
			position = 2,
			titleSection = "firstTitle"
	)
	default Options options() { return Options.BANK; }

	@ConfigItem(
			keyName = "bankIndicator",
			name = "Bank",
			description = "Displays the an indicator if the bank interface is open.",
			position = 3,
			titleSection = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default boolean displayBank() { return false; }

	@ConfigItem(
			keyName = "bankIndicatorsLocation",
			name = "Indicator location",
			description = "Indicator location, format to use: x:y:width:height e.g 100:100:10:10",
			position = 4,
			titleSection = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default String bankLocation() { return "150:0:5:5"; }

	@ConfigItem(
			keyName = "bankIndicatorsColor",
			name = "Indicator Color",
			description = "Indicator Color.",
			position = 5,
			titleSection = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default Color bankColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "depositIndicator",
			name = "Deposit",
			description = "Displays the an indicator if the deposit inventory interface is open.",
			position = 6,
			titleSection = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default boolean displayDeposit() { return false; }

	@ConfigItem(
			keyName = "depositIndicatorLocation",
			name = "Indicator location",
			description = "Indicator location, format to use: x:y:width:height e.g 100:100:10:10",
			position = 7,
			titleSection = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default String depositLocation() { return "150:0:5:5"; }

	@ConfigItem(
			keyName = "depositIndicatorColor",
			name = "Indicator Color",
			description = "Indicator Color.",
			position = 8,
			titleSection = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "BANK"
	)
	default Color depositColor() { return Color.GREEN; }

	@ConfigItem(
			keyName = "chatboxMakeIndicator",
			name = "Make / Create",
			description = "Displays the an indicator if the deposit inventory interface is open.",
			position = 6,
			titleSection = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "CHATBOX"
	)
	default boolean displayChatboxMake() { return false; }

	@ConfigItem(
			keyName = "chatboxMakeIndicatorLocation",
			name = "Indicator location",
			description = "Indicator location, format to use: x:y:width:height e.g 100:100:10:10",
			position = 7,
			titleSection = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "CHATBOX"
	)
	default String makeLocation() { return "155:0:5:5"; }

	@ConfigItem(
			keyName = "chatboxMakeIndicatorColor",
			name = "Indicator Color",
			description = "Indicator Color.",
			position = 8,
			titleSection = "firstTitle",
			hidden = true,
			unhide = "configType",
			unhideValue = "CHATBOX"
	)
	default Color makeColor() { return Color.GREEN; }

}