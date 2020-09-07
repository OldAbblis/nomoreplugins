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
package net.runelite.client.plugins.marker;

import java.awt.*;
import javax.inject.Inject;

import net.runelite.api.*;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class MarkerOverlay extends Overlay
{

    private final Client client;
    private final MarkerPlugin plugin;
    private final MarkerConfig config;

    @Inject
    MarkerOverlay(final Client client, final MarkerPlugin plugin, final MarkerConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        Player player = client.getLocalPlayer();
        if (player == null)
        {
            return null;
        }

        if (config.highlightNPC())
        {
            renderNPC(graphics, player);
        }
        return null;
    }

    private void renderNPC(Graphics2D graphics, Player player)
    {
        for (NPC npc : plugin.getWorldNPC())
        {
            if (npc == null)
            {
                continue;
            }
            if (config.npcLineOfSight())
            {
                if (!plugin.hasLineOfSight(player, npc))
                {
                    continue;
                }
            }
            Rectangle shape = npc.getConvexHull().getBounds();
            if (shape == null)
            {
                continue;
            }
            int x = (int) shape.getCenterX() - 2;
            int y = (int) shape.getCenterY() - 2;
            graphics.setColor(getNPCColor(graphics, npc));
            graphics.fillRect(x, y, 4, 4);
        }
    }

    private Color getNPCColor(Graphics2D graphics, NPC npc)
    {

        return Color.GREEN;
    }

}
