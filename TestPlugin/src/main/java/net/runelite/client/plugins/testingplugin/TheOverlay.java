package net.runelite.client.plugins.testingplugin;

import net.runelite.api.*;
import net.runelite.client.plugins.nmutils.Utils;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;
import java.util.Arrays;

public class TheOverlay extends Overlay {

    private final Client client;
    private final ThePlugin plugin;
    private final TheConfig config;
    private final Utils utils;

    @Inject
    TheOverlay(Client client, ThePlugin plugin, TheConfig config, Utils utils)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.utils = utils;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D g)
    {
        return null;
    }
}
