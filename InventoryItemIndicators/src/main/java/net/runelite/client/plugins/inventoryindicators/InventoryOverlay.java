package net.runelite.client.plugins.inventoryindicators;

import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

import javax.inject.Inject;
import java.awt.*;
import java.util.Collection;
import java.util.HashMap;

public class InventoryOverlay extends WidgetItemOverlay {

    private final Client client;
    private final IIPlugin plugin;
    private final IIConfig config;

    @Inject
    public InventoryOverlay(Client client, IIPlugin plugin, IIConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        showOnInventory();
        showOnBank();
    }

    @Override
    protected void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem itemWidget) {

        plugin.getInventoryItems().forEach((item, color) -> {

            if (itemId == item.getId())
            {
                int boxSize = config.containMarkerSize();
                int x = (int) itemWidget.getCanvasBounds().getCenterX() - boxSize/2;
                int y = (int) itemWidget.getCanvasBounds().getCenterY() - boxSize/2;
                graphics.setColor(color);
                graphics.fillRect(x, y, boxSize, boxSize);
            }
        });

    }
}
