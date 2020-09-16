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
package net.runelite.client.plugins.mlm;

import java.awt.*;
import javax.inject.Inject;

import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.plugins.nmutils.Utils;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.http.api.worlds.WorldRegion;
import org.w3c.dom.css.Rect;

public class TheOverlay extends Overlay
{
    private final Client client;
    private final ThePlugin plugin;
    private final TheConfig config;
    private final Utils utils;

    @Inject
    TheOverlay(final Client client, final ThePlugin plugin, final TheConfig config, Utils utils)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.utils = utils;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    static WorldPoint hopperWP = new WorldPoint(3748,5672,0);
    Player player;

    @Override
    public Dimension render(Graphics2D graphics)
    {
        player = client.getLocalPlayer();
        if (player == null)
        {
            return null;
        }
        if (player.getWorldLocation().getRegionID() != 14936)
        {
            return null;
        }
        LocalPoint playerLP = player.getLocalLocation();
        if (playerLP == null)
        {
            return null;
        }

        // Display upper level objects.
        if (plugin.isPlayerUpstairs())
        {
            renderUpperObjects(graphics);
        }
        else
        {
            renderLowerObjects(graphics);
            if (!config.enableRockfallObjectIndicator())
            {
                return null;
            }
            for (GameObject gameObject : plugin.getClosestRockfall())
            {
                if (gameObject == null)
                {
                    continue;
                }
                Shape shape = gameObject.getConvexHull();
                if (shape == null)
                {
                    continue;
                }
                Rectangle bounds = shape.getBounds();
                if (bounds == null)
                {
                    continue;
                }
                utils.renderCentrePoint(graphics, bounds, config.rockfallObjectIndicatorColor(), config.rockfallObjectIndicatorSize());
            }
        }
        return null;
    }

    private void renderUpperObjects(Graphics2D graphics)
    {
        plugin.getUpperObjects().forEach((tile, tileObject) ->
        {
            if (tileObject != null)
            {
                renderOptions(graphics, tileObject);
            }
        });
        if (config.enableUpperVeinIndicator())
        {
            if (config.enableUpperNorthWestOres())
            {
                plugin.getUpperNorthWestVeins().forEach((tile, tileObject) ->
                {
                    if (tileObject != null)
                    {
                        renderCentrePoint(graphics, tileObject, config.upperVeinColor(), config.upperVeinSize());
                    }
                });
            }
            if (config.enableUpperNorthEastOres())
            {
                plugin.getUpperNorthEastVeins().forEach((tile, tileObject) ->
                {
                    if (tileObject != null)
                    {
                        renderCentrePoint(graphics, tileObject, config.upperVeinColor(), config.upperVeinSize());
                    }
                });
            }
            if (config.enableUpperSouthOres())
            {
                plugin.getUpperSouthVeins().forEach((tile, tileObject) ->
                {
                    if (tileObject != null)
                    {
                        renderCentrePoint(graphics, tileObject, config.upperVeinColor(), config.upperVeinSize());
                    }
                });
            }
        }
    }

    private void renderLowerObjects(Graphics2D graphics)
    {
        plugin.getLowerObjects().forEach((tile, tileObject) ->
        {
            if (tileObject != null)
            {
                renderOptions(graphics, tileObject);
            }
        });
        if (config.enableHopperObjectIndicator())
        {
            utils.renderTileCentrePoint(graphics, hopperWP, config.hopperObjectIndicatorColor(), config.hopperObjectIndicatorSize());
        }
    }

    private void renderOptions(Graphics2D graphics, TileObject tileObject)
    {
        WorldPoint playerWP = player.getWorldLocation();
        if (playerWP == null)
        {
            return;
        }
        WorldPoint objectWP = tileObject.getWorldLocation();
        if (objectWP == null)
        {
            return;
        }

        if (config.enableLadderIndicator() && ObjectsList.LADDERS.contains(tileObject.getId()))
        {
            renderCentrePoint(graphics, tileObject, config.ladderColor(), config.ladderSize());
        }
        if (ObjectsList.FIX_WATER_WHEEL.contains(tileObject.getId()))
        {
            if (config.enableWaterWheelIndicator())
            {
                renderCentrePoint(graphics, tileObject, config.waterWheelColor(), config.waterWheelSize());
            }
            if (config.enableWaterWheelSceneIndicator() && (plugin.getNumberOfBrokenWaterWheels() >= config.amountOfBrokenWaterWheelToDisplaySceneIndicator()))
            {
                renderSceneIndicator(graphics, config.waterWheelSceneIndicatorColor(), utils.getIndicatorLocation(config.waterWheelSceneIndicatorLocation()));
            }
        }
        if (config.enableSackObjectIndicator() && ObjectsList.EMPTY_SACK.contains(tileObject.getId()))
        {
            renderCentrePoint(graphics, tileObject, config.sackObjectIndicatorColor(), config.sackObjectIndicatorSize());
        }
        if (config.enableBankObjectIndicator() && ObjectsList.BANK_CHEST.contains(tileObject.getId()))
        {
            renderCentrePoint(graphics, tileObject, config.bankObjectIndicatorColor(), config.bankObjectIndicatorSize());
        }
        if (config.enableShortcutObjectIndicator() && ObjectsList.SHORTCUTS.contains(tileObject.getId()))
        {
            if (playerWP.getX() > 3762 && objectWP.getX() > 3762 || playerWP.getX() < 3762 && objectWP.getX() < 3762)
            {
                renderCentrePoint(graphics, tileObject, config.shortcutObjectIndicatorColor(), config.shortcutObjectIndicatorSize());
            }
        }
        if (config.enableLowerVeinIndicator() && !plugin.isPlayerUpstairs() && ObjectsList.ORE_VEINS.contains(tileObject.getId()))
        {
            renderCentrePoint(graphics, tileObject, config.lowerVeinColor(), config.lowerVeinSize());
        }
    }

    private void renderCentrePoint(Graphics2D graphics, TileObject tileObject, Color color, int boxSize)
    {
        Shape shape = tileObject.getClickbox();
        if (shape == null)
        {
            return;
        }
        Rectangle bounds = shape.getBounds();
        utils.renderCentrePoint(graphics, bounds, color, boxSize);
    }

    private void renderSceneIndicator(Graphics2D graphics, Color color, int[] i)
    {
        graphics.setColor(color);
        graphics.fillRect(i[0], i[1], i[2], i[3]);
    }
}
