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
package net.runelite.client.plugins.spellbookindicators;

import net.runelite.client.config.*;

import java.awt.Color;


@ConfigGroup("spellbookindicators")
public interface SpellbookConfig extends Config
{
    @ConfigTitleSection(
            keyName = "firstTitle",
            name = "Indicator Options",
            description = "",
            position = 1
    )
    default Title firstTitle() { return new Title(); }

    @ConfigItem(
            keyName = "boxSize",
            name = "Indicator size",
            description = "The size of the indicator.",
            position = 2,
            titleSection = "firstTitle"
    )
    default int boxSize() { return 4; }

    @ConfigItem(
            keyName = "color",
            name = "Indicator color",
            description = "The color of the indicator.",
            position = 3,
            titleSection = "firstTitle"
    )
    default Color color() { return Color.RED; }

    /*

    @ConfigItem(
            keyName = "hideUncastableSpells",
            name = "Hide uncastable spells",
            description = "Hide spells that you do not have the runes, or level to cast.",
            position = 3,
            titleSection = "firstTitle"
    )
    default boolean hideUncastableSpells() { return false; }

    @ConfigTitleSection(
            keyName = "secondTitle",
            name = "Spellbook Type",
            description = "",
            position = 4
    )
    default Title secondTitle() { return new Title(); }

    @ConfigItem(
            keyName = "spellbookType",
            name = "Display options for",
            description = "Drop down menu to display configuration options.",
            position = 5,
            titleSection = "secondTitle"
    )
    default SpellbookType spellbookType() { return SpellbookType.NORMAL; }

    @ConfigItem(
            keyName = "lumbridgeHomeTeleport",
            name = "Lumbridge Home Teleport",
            description = "Enable the indicator.",
            position = 6,
            hidden = true,
            unhide = "spellbookType",
            unhideValue = "NORMAL",
            titleSection = "secondTitle"
    )
    default boolean lumbridgeHomeTeleport() {
        return false;
    }

    @ConfigItem(
            keyName = "lumbridgeHomeTeleportColor",
            name = "Color",
            description = "Indicator color.",
            position = 7,
            hidden = true,
            unhide = "spellbookType",
            unhideValue = "NORMAL",
            titleSection = "secondTitle"
    )
    default Color lumbridgeHomeTeleportColor() { return Color.RED; }

    @ConfigItem(
            keyName = "windStrike",
            name = "Wind Strike",
            description = "Enable the indicator.",
            position = 8,
            hidden = true,
            unhide = "spellbookType",
            unhideValue = "NORMAL",
            titleSection = "secondTitle"
    )
    default boolean windStrike() {
        return false;
    }

    @ConfigItem(
            keyName = "windStrikeColor",
            name = "Color",
            description = "Indicator color.",
            position = 9,
            hidden = true,
            unhide = "spellbookType",
            unhideValue = "NORMAL",
            titleSection = "secondTitle"
    )
    default Color windStrikeColor() { return Color.RED; }

    @ConfigItem(
            keyName = "confuse",
            name = "Confuse",
            description = "Enable the indicator.",
            position = 10,
            hidden = true,
            unhide = "spellbookType",
            unhideValue = "NORMAL",
            titleSection = "secondTitle"
    )
    default boolean confuse() {
        return false;
    }

    @ConfigItem(
            keyName = "confuseColor",
            name = "Color",
            description = "Indicator color.",
            position = 11,
            hidden = true,
            unhide = "spellbookType",
            unhideValue = "NORMAL",
            titleSection = "secondTitle"
    )
    default Color confuseColor() { return Color.RED; }

    @ConfigItem(
            keyName = "enchantCrossbowBolt",
            name = "Enchant Crossbow Bolt",
            description = "Enable the indicator.",
            position = 12,
            hidden = true,
            unhide = "spellbookType",
            unhideValue = "NORMAL",
            titleSection = "secondTitle"
    )
    default boolean enchantCrossbowBolt() {
        return false;
    }

    @ConfigItem(
            keyName = "enchantCrossbowBoltColor",
            name = "Color",
            description = "Indicator color.",
            position = 13,
            hidden = true,
            unhide = "spellbookType",
            unhideValue = "NORMAL",
            titleSection = "secondTitle"
    )
    default Color enchantCrossbowBoltColor() { return Color.RED; }

    @ConfigItem(
            keyName = "waterStrike",
            name = "Water Strike",
            description = "Enable the indicator.",
            position = 14,
            hidden = true,
            unhide = "spellbookType",
            unhideValue = "NORMAL",
            titleSection = "secondTitle"
    )
    default boolean waterStrike() {
        return false;
    }

    @ConfigItem(
            keyName = "waterStrikeColor",
            name = "Color",
            description = "Indicator color.",
            position = 15,
            hidden = true,
            unhide = "spellbookType",
            unhideValue = "NORMAL",
            titleSection = "secondTitle"
    )
    default Color waterStrikeColor() { return Color.RED; }

    @ConfigItem(
            keyName = "lvl1Enchant",
            name = "Lvl-1 Enchant",
            description = "Enable the indicator.",
            position = 16,
            hidden = true,
            unhide = "spellbookType",
            unhideValue = "NORMAL",
            titleSection = "secondTitle"
    )
    default boolean lvl1Enchant() {
        return false;
    }

    @ConfigItem(
            keyName = "lvl1EnchantColor",
            name = "Color",
            description = "Indicator color.",
            position = 17,
            hidden = true,
            unhide = "spellbookType",
            unhideValue = "NORMAL",
            titleSection = "secondTitle"
    )
    default Color lvl1EnchantColor() { return Color.RED; }

    @ConfigItem(
            keyName = "earthStrike",
            name = "Earth Strike",
            description = "Enable the indicator.",
            position = 18,
            hidden = true,
            unhide = "spellbookType",
            unhideValue = "NORMAL",
            titleSection = "secondTitle"
    )
    default boolean earthStrike() {
        return false;
    }

    @ConfigItem(
            keyName = "earthStrikeColor",
            name = "Color",
            description = "Indicator color.",
            position = 19,
            hidden = true,
            unhide = "spellbookType",
            unhideValue = "NORMAL",
            titleSection = "secondTitle"
    )
    default Color earthStrikeColor() { return Color.RED; }*/
}