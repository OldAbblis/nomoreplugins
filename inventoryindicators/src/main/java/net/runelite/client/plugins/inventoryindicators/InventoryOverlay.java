package net.runelite.client.plugins.inventoryindicators;

import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;

public class InventoryOverlay extends Overlay {

    private final Client client;
    private final IIPlugin plugin;
    private final IIConfig config;

    @Inject
    public InventoryOverlay(Client client, IIPlugin plugin, IIConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        return null;
    }
}
