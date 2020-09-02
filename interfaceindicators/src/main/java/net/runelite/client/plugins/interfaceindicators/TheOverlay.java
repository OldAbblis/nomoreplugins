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
package net.runelite.client.plugins.interfaceindicators;

import java.awt.*;
import java.util.regex.Pattern;
import javax.inject.Inject;

import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

public class TheOverlay extends Overlay
{

    private final Client client;
    private final ThePlugin plugin;
    private final TheConfig config;

    @Inject
    TheOverlay(final Client client, final ThePlugin plugin, final TheConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (config.displayBank())
        {
            Widget bank = client.getWidget(WidgetInfo.BANK_CONTAINER);
            if (bank != null && !bank.isHidden())
            {
                renderG(graphics, config.bankColor(), config.bankLocation().split(Pattern.quote(":")));
            }
        }
        if (config.displayDeposit())
        {
            Widget deposit = client.getWidget(WidgetInfo.DEPOSIT_BOX_INVENTORY_ITEMS_CONTAINER);
            if (deposit != null && !deposit.isHidden())
            {
                renderG(graphics, config.depositColor(), config.depositLocation().split(Pattern.quote(":")));
            }
        }
        if (config.displayChatboxMake())
        {
            Widget chatboxMakeAll = client.getWidget(270, 12);
            if (chatboxMakeAll != null && !chatboxMakeAll.isHidden())
            {
                renderG(graphics, config.makeColor(), config.makeLocation().split(Pattern.quote(":")));
            }
        }
        return null;
    }

    private void renderG(Graphics2D graphics, Color color, String[] s)
    {
        graphics.setColor(color);
        graphics.fillRect(getParsedInt(s,0),
                getParsedInt(s,1),
                getParsedInt(s,2),
                getParsedInt(s,3));
    }

    private int getParsedInt(String[] strings, int number)
    {
        return Integer.parseInt(strings[number]);
    }
}
