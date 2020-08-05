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
package net.runelite.client.plugins.spellbookindicators;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.inject.Inject;

import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class SpellbookOverlay extends Overlay
{

    private final Client client;
    private final SpellbookPlugin plugin;
    private final SpellbookConfig config;

    @Inject
    SpellbookOverlay(final Client client, final SpellbookPlugin plugin, final SpellbookConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    private Player player;
    private int boxSize;

    @Override
    public Dimension render(Graphics2D graphics) {
        player = client.getLocalPlayer();
        if (player == null)
        {
            return null;
        }

        if (client.getWidget(WidgetInfo.SPELLBOOK) == null || client.getWidget(WidgetInfo.SPELLBOOK).isHidden())
        {
            return null;
        }

        boxSize = config.boxSize();



        /*

        switch (config.spellbookType())
        {
            case NORMAL:
                if (config.lumbridgeHomeTeleport())
                    renderWidget(graphics, client.getWidget(WidgetInfo.SPELL_LUMBRIDGE_HOME_TELEPORT), config.lumbridgeHomeTeleportColor());
                if (config.windStrike())
                    renderWidget(graphics, client.getWidget(WidgetInfo.SPELL_WIND_STRIKE), config.windStrikeColor());
                if (config.confuse())
                    renderWidget(graphics, client.getWidget(WidgetInfo.SPELL_CONFUSE), config.windStrikeColor());
                if (config.confuse())
                    renderWidget(graphics, client.getWidget(WidgetInfo.SPELL_CONFUSE), config.confuseColor());
                if (config.enchantCrossbowBolt())
                    renderWidget(graphics, client.getWidget(WidgetInfo.SPELL_ENCHANT_CROSSBOW_BOLT), config.enchantCrossbowBoltColor());
                if (config.waterStrike())
                    renderWidget(graphics, client.getWidget(WidgetInfo.SPELL_WATER_STRIKE), config.waterStrikeColor());
                if (config.lvl1Enchant())
                    renderWidget(graphics, client.getWidget(WidgetInfo.SPELL_LVL_1_ENCHANT), config.lvl1EnchantColor());
                if (config.earthStrike())
                    renderWidget(graphics, client.getWidget(WidgetInfo.SPELL_EARTH_STRIKE), config.earthStrikeColor());
                break;
            case ANCIENT:
                break;
            case LUNAR:
                break;
            case ZEAH:
                break;
        }

         */

        return null;
    }

    private void renderWidget(Graphics2D graphics, Widget widget, Color color)
    {
        if (widget == null || widget.isHidden())
        {
            return;
        }

        int x = (int) widget.getBounds().getCenterX();
        int y = (int) widget.getBounds().getCenterY();
        graphics.setColor(color);
        graphics.fillRect(x - (boxSize / 2), y - (boxSize / 2), boxSize, boxSize);
    }

}
