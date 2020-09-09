package net.runelite.client.plugins.testingplugin;

import net.runelite.api.*;
import net.runelite.client.plugins.nmutils.Utils;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;
import java.util.Arrays;

public class TestingPluginOverlay extends Overlay {

    private final Client client;
    private final TestingPlugin plugin;
    private final TestingPluginConfig config;
    private final Utils utils;

    @Inject
    TestingPluginOverlay(Client client, TestingPlugin plugin, TestingPluginConfig config, Utils utils)
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
        if (config.location().isEmpty())
        {
            int[] canvasIndicatorLocation = utils.getIndicatorLocation(config.location());
            System.out.println(Arrays.toString(canvasIndicatorLocation));
        }
        return null;
    }
}
